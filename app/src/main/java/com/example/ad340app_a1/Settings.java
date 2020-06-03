package com.example.ad340app_a1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    // these are table fields for the database
    @PrimaryKey
    @NonNull
    private String email = "";

    @NonNull
    @ColumnInfo(name = "reminder_time")
    private String reminderTime = "None";

    @NonNull
    @ColumnInfo(name = "distance")
    private int distance = 50;

    @ColumnInfo(name = "gender")
    private String gender = "";

    @NonNull
    @ColumnInfo(name = "privacy")
    private String privacy = "Public";

    @NonNull
    @ColumnInfo(name = "age_lo")
    private int ageLo = 18;

    @NonNull
    @ColumnInfo(name = "age_hi")
    private int ageHi = 30;

    // Getters and setters
    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NonNull
    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @NonNull
    public int getAgeLo() {
        return ageLo;
    }

    public void setAgeLo(int ageLo) {
        this.ageLo = ageLo;
    }

    public int getAgeHi() {
        return ageHi;
    }

    public void setAgeHi(int ageHi) {
        this.ageHi = ageHi;
    }


}
