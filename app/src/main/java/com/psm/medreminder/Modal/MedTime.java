package com.psm.medreminder.Modal;

import java.util.ArrayList;

public class MedTime {
    private String time;
    private ArrayList<Medicine> medicines;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }
}
