package com.ryde.chris.ryde;

import
        android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;



public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));
    }
}
