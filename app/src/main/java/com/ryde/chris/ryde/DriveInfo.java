package com.ryde.chris.ryde;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by chris on 2/13/2016.
 */
public class DriveInfo {
    User driver;
    DepartInfo departFrom;
    DepartInfo departTo;

    class DepartInfo {
        DepartInfo(int hour, int minute,  boolean isAm) {
            this.hour = hour;
            this.minute = minute;
            this.isAm = isAm;
        }
        int hour;
        int minute;
        boolean isAm;
    }
    public DriveInfo(User driver, int hourDepartTo, int minuteDepartTo, boolean isAmDepartTo,
                     int hourDepartFrom, int minuteDepartFrom, boolean isAmDepartFrom) {
        this.driver = driver;
        departFrom = new DepartInfo(hourDepartFrom, minuteDepartFrom, isAmDepartFrom);
        departTo = new DepartInfo(hourDepartTo, minuteDepartTo, isAmDepartTo);
    }
    public void update(View updatedDriveInfo) {
        driver = ((User)((Spinner)updatedDriveInfo.findViewById(R.id.drivers)).getSelectedItem());
        departTo.hour = Integer.parseInt(((TextView)updatedDriveInfo.findViewById(R.id.hourDepartToView)).getText().toString());
        departTo.minute = Integer.parseInt(((TextView) updatedDriveInfo.findViewById(R.id.minuteDepartToView)).getText().toString());
        departFrom.hour = Integer.parseInt(((TextView)updatedDriveInfo.findViewById(R.id.hourDepartFromView)).getText().toString());
        departFrom.minute = Integer.parseInt(((TextView)updatedDriveInfo.findViewById(R.id.minuteDepartFromView)).getText().toString());
    }
}

