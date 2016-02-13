package com.ryde.chris.ryde;

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
}

