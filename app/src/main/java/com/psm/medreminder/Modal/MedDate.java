package com.psm.medreminder.Modal;

import java.util.ArrayList;

public class MedDate {

    private String date;
    private ArrayList<MedTime> medTimes;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<MedTime> getMedTimes() {
        return medTimes;
    }

    public void setMedTimes(ArrayList<MedTime> medTimes) {
        this.medTimes = medTimes;
    }


}
