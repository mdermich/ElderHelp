package com.example.oldpeoplehelp;

public class Medicine {
    public String idSchedule, currentUserId, pillName, scheduleDate, dose_quantity;
    public Integer hour,minute;

    public Medicine() {
    }

    public Medicine(String currentUserId, String pillName, Integer hour
            ,Integer minute,String scheduleDate, String dose_quantity) {
        this.currentUserId = currentUserId;
        this.pillName = pillName;
        this.hour = hour;
        this.minute = minute;
        this.scheduleDate = scheduleDate;
        this.dose_quantity = dose_quantity;
    }

    public String getIdEvent() {
        return idSchedule;
    }

    public void setIdEvent(String idEvent) {
        this.idSchedule = idSchedule;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String eventDate) {
        this.pillName = eventDate;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDose_quantity() {
        return dose_quantity;
    }

    public void setDose_quantity(String eventTime) {
        this.dose_quantity = dose_quantity;
    }
}
