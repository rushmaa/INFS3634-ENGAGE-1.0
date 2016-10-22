package infs3634.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

import java.io.Serializable;

/**
 * Created by SONY on 10/15/2016.
 */

public class Consultation {

    private int consultationID;
    private String name;
    private String date;
    private String time;
    private int status;
    private String note;

    public Consultation() {
        this(-1, "", "", "", 1, "");
    }

    public Consultation(int consultationID, String name, String date, String time, int status, String note) {
        this.consultationID = consultationID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
        this.note = note;
    }

    public int getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(int consultationID) {
        this.consultationID = consultationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
