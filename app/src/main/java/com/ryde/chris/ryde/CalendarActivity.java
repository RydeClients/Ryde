package com.ryde.chris.ryde;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CalendarActivity extends TouchActivity {
    private static final int MAX_CELLS = 42;

    private LinearLayout header;
    private Button btnPrev;
    private Button nextButton;
    private GridView calGrid;
    private TextView monthDisplay;
    private Calendar calendarDisplay;
    private Map<Date, User> groupSchedule;
    private Group mGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent fromMainActivity = getIntent();
        Group theGroup = fromMainActivity.getParcelableExtra("group");
        mGroup = theGroup;
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
        calGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date selected = (Date)parent.getItemAtPosition(position);
                Date selectedInMap = dateInMap(groupSchedule, selected);
                if(selectedInMap == null) {
                    //new dialog
                } else {
                    //existing dialog
                    AlertDialog.Builder driveInfoDialogBuilder = new AlertDialog.Builder(CalendarActivity.this, R.style.AppTheme);
                    final LayoutInflater inflateDriveInfoDialog = (LayoutInflater) CalendarActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View driveInfoView = inflateDriveInfoDialog.inflate(R.layout.drive_info, null, false);
                    final User driver = groupSchedule.get(selectedInMap);
                    ((TextView)driveInfoView.findViewById(R.id.memberName)).setText(driver.getName().split(" ")[0]);
                    ImageView driverProfileImage = (ImageView)driveInfoView.findViewById(R.id.profileImage);
                    RelativeLayout.LayoutParams driveLayoutParams = (RelativeLayout.LayoutParams)driverProfileImage.getLayoutParams();
                    driveLayoutParams.setMargins(400, 75, 0, 0);
                    driveLayoutParams.height = 200;
                    driverProfileImage.setImageResource(driver.getProfileImage());
                    final AlertDialog driveInfoDialog = driveInfoDialogBuilder.create();
                    driveInfoDialog.setView(driveInfoView);
                    WindowManager.LayoutParams windowParams = driveInfoDialog.getWindow().getAttributes();
                    windowParams.dimAmount = 0.75f;
                    windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    driveInfoView.findViewById(R.id.editDriveInfo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            driveInfoDialog.dismiss();
                            AlertDialog.Builder editDriveInfoDialogBuilder = new AlertDialog.Builder(CalendarActivity.this, R.style.AppTheme);
                            final View editDriveInfoView = inflateDriveInfoDialog.inflate(R.layout.edit_drive_info, null, false);
//                            RelativeLayout.LayoutParams driveLayoutParams = (RelativeLayout.LayoutParams)driverProfileImage.getLayoutParams();
//                            driveLayoutParams.setMargins(400, 75, 0, 0);
//                            driveLayoutParams.height = 200;
                            editDriveInfoDialogBuilder.setView(editDriveInfoView);
                            int[] assignClickActions = {R.id.increaseHourDepartTo, R.id.decreaseHourDepartTo, R.id.increaseMinDepartTo, R.id.decreaseMinDepartTo,
                                 R.id.increaseHourDepartTo, R.id.increaseHourDepartFrom, R.id.decreaseHourDepartFrom, R.id.increaseMinDepartFrom, R.id.decreaseMinDepartFrom,
                                    R.id.increaseHourDepartFrom};
                            assignTouchEffects(editDriveInfoView, assignClickActions);
                            setUpHourText(editDriveInfoView, R.id.hourDepartToView, R.id.increaseHourDepartTo, R.id.decreaseHourDepartTo,
                                    R.id.amPmDepartTo);
                            setUpHourText(editDriveInfoView, R.id.hourDepartFromView, R.id.increaseHourDepartFrom, R.id.decreaseHourDepartFrom,
                                    R.id.amPmDepartFrom);
                            setUpMinuteText(editDriveInfoView, R.id.minuteDepartToView, R.id.increaseMinDepartTo,
                                    R.id.decreaseMinDepartTo);
                            setUpMinuteText(editDriveInfoView, R.id.minuteDepartFromView, R.id.increaseMinDepartFrom,
                                    R.id.decreaseMinDepartFrom);
                            setUpDrivers(editDriveInfoView);
                            final AlertDialog editDriveInfoDialog = editDriveInfoDialogBuilder.create();
                            editDriveInfoDialog.getWindow().getAttributes().y = 20;
                            WindowManager.LayoutParams windowParams = editDriveInfoDialog.getWindow().getAttributes();
                            windowParams.dimAmount = 0.75f;
                            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                            editDriveInfoDialog.getWindow().setLayout(1200, 1525);
                            editDriveInfoDialog.show();
                        }
                    });
                    driveInfoDialog.getWindow().setLayout(1000, 850);
                    driveInfoDialog.show();

                }
            }
        });
        monthDisplay = (TextView)findViewById(R.id.calendar_date_display);
        updateCalendar();
    }
    //set up the spinner of drivers
    private void setUpDrivers(View parentView) {
        Spinner drivers = (Spinner)parentView.findViewById(R.id.drivers);
        MemberAdapter userArrayAdapter = new MemberAdapter(this, R.layout.member_layout, mGroup.getUsers());
        drivers.setAdapter(userArrayAdapter);
    }
    private void setUpHourText(View parent, int idOfHourText, int idOfIncreaseButton, int idOfDecreaseButton, int idOfAmPm) {
        final TextView  hourTextView = (TextView)parent.findViewById(idOfHourText);
        final ToggleButton amPm = (ToggleButton)parent.findViewById(idOfAmPm);
        parent.findViewById(idOfIncreaseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextHour = (Integer.parseInt(hourTextView.getText().toString()) % 12) + 1;
                if(nextHour == 12) {
                    amPm.toggle();
                }
                hourTextView.setText("" + nextHour);
            }
        });
        parent.findViewById(idOfDecreaseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextHour = (Integer.parseInt(hourTextView.getText().toString()) ) - 1;
                nextHour = nextHour == 0? 12:nextHour;
                if(nextHour == 11) {
                    amPm.toggle();
                }
                hourTextView.setText("" + nextHour);
            }
        });
    }
    private void setUpMinuteText(View parent, int idOfMinuteText, int idOfIncreaseButton, int idOfDecreaseButton) {
        final TextView minuteTextView = (TextView)parent.findViewById(idOfMinuteText);
        final Button increaseButton = (Button)parent.findViewById(idOfIncreaseButton);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextMinute = (Integer.parseInt(minuteTextView.getText().toString()) + 1) % 60;
                minuteTextView.setText("" + nextMinute);
                if(nextMinute < 10) {
                    minuteTextView.setText("0" + nextMinute);
                } else {
                    minuteTextView.setText("" + nextMinute);
                }
            }
        });
        final Button decreaseButton = (Button)parent.findViewById(idOfDecreaseButton);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextMinute = Integer.parseInt(minuteTextView.getText().toString()) - 1;
                if(nextMinute == -1) {
                    nextMinute = 59;
                }
                if(nextMinute < 10) {
                    minuteTextView.setText("0" + nextMinute);
                } else {
                    minuteTextView.setText("" + nextMinute);
                }
            }
        });
    }

    private void assignTouchEffects(View parentLayout, int[] idButtonsApplyTouch) {
        for(int k=0; k<idButtonsApplyTouch.length; k++) {
            setTouchNClick(parentLayout.findViewById(idButtonsApplyTouch[k]));
        }
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

    //In Date.Java, equals compares exact miliseconds, need to write our own
    private static Date dateInMap(Map<Date, User> datesMap, Date key) {
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

    }
}
