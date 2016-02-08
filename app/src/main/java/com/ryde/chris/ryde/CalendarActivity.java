package com.ryde.chris.ryde;

import android.content.Context;
import android.content.Intent;
import
        android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CalendarActivity extends AppCompatActivity {
    private static final int MAX_CELLS = 42;

    private LinearLayout header;
    private Button btnPrev;
    private Button nextButton;
    private GridView calGrid;
    private TextView monthDisplay;
    private Calendar calendarDisplay;
    private Map<Date, User> groupSchedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent fromMainActivity = getIntent();
        Group theGroup = fromMainActivity.getParcelableExtra("group");
        groupSchedule = theGroup.getSchedule();
        calendarDisplay = Calendar.getInstance();
        setContentView(R.layout.activity_calendar);
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (Button) findViewById(R.id.calendar_prev_button);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDisplay.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });
        nextButton = (Button) findViewById(R.id.calendar_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDisplay.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });
        calGrid = (GridView) findViewById(R.id.calendar_grid);
        monthDisplay = (TextView)findViewById(R.id.calendar_date_display);
        updateCalendar();
    }

    private void updateCalendar() {
        List<Date> days = new ArrayList<Date>();
        Calendar calendarData = (Calendar)calendarDisplay.clone();
        while(calendarData.get(Calendar.DAY_OF_MONTH) > 1) {
            calendarData.add(Calendar.DAY_OF_MONTH, -1);
        }
        int monthBeginningCell = calendarData.get(Calendar.DAY_OF_WEEK) - 1;
        calendarData.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        while(days.size() < MAX_CELLS) {
            days.add(calendarData.getTime());
            calendarData.add(Calendar.DAY_OF_MONTH, 1);
        }
        calGrid.setAdapter(new RydeCalendarAdapter(this, R.layout.ryde_calendar_date, days));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyy");
        monthDisplay.setText(simpleDateFormat.format(calendarDisplay.getTime()));
    }

    private class RydeCalendarAdapter extends ArrayAdapter<Date> {
        private List<Date> calData;
        private Context mContext;
        private int mResource;

        public RydeCalendarAdapter(Context theContext, int resource, List<Date> dates) {
            super(theContext, resource, dates);
            mContext = theContext;
            mResource = resource;
            calData = dates;
        }
        @Override
        public int getCount() {
            return calData.size();
        }

        @Override
        public Date getItem(int position) {
            return calData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(mResource, null);
            }
            Date today = new Date();
            Date calDate = getItem(position);
            Date dateInMap = dateInMap(groupSchedule, calDate);
            if(dateInMap != null) {
                ImageView profileView = (ImageView) convertView.findViewById(R.id.driverPhoto);
                profileView.setImageResource(groupSchedule.get(dateInMap).getProfileImage());
            }
            TextView date = (TextView)convertView.findViewById(R.id.date);
            if(today.getMonth() != calDate.getMonth() || today.getYear() != calDate.getYear()) {
                date.setTextColor(getResources().getColor(R.color.greyed_out));
            } else if (today.getDate() == calDate.getDate()) {
                date.setTextColor(getResources().getColor(R.color.today));
            }
            date.setText(String.valueOf(calDate.getDate()));
            return convertView;
        }
        //In Date.Java, equals compares exact miliseconds, need to write our own
        private Date dateInMap(Map<Date, User> datesMap, Date key) {
            Calendar keyDate = Calendar.getInstance();
            keyDate.setTime(key);
            for(Date date: datesMap.keySet()) {
                Calendar calDate = Calendar.getInstance();
                calDate.setTime(date);
                if(calDate.get(Calendar.DAY_OF_MONTH) == keyDate.get(Calendar.DAY_OF_MONTH)  &&
                        calDate.get(Calendar.MONTH) == keyDate.get(Calendar.MONTH)
                        && calDate.get(Calendar.YEAR) == keyDate.get(Calendar.YEAR)) {
                    return date;
                }
            }
            return null;
        }
    }
}
