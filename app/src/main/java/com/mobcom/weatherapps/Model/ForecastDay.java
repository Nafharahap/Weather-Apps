package com.mobcom.weatherapps.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ForecastDay implements Parcelable {
    private String date;
    private Day day;
    private ArrayList<Hour> hour;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public ArrayList<Hour> getHour() {
        return hour;
    }

    public void setHour(ArrayList<Hour> hour) {
        this.hour = hour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeParcelable(this.day, flags);
        dest.writeTypedList(this.hour);
    }

    public void readFromParcel(Parcel source) {
        this.date = source.readString();
        this.day = source.readParcelable(Day.class.getClassLoader());
        this.hour = source.createTypedArrayList(Hour.CREATOR);
    }

    public ForecastDay() {
    }

    protected ForecastDay(Parcel in) {
        this.date = in.readString();
        this.day = in.readParcelable(Day.class.getClassLoader());
        this.hour = in.createTypedArrayList(Hour.CREATOR);
    }

    public static final Parcelable.Creator<ForecastDay> CREATOR = new Parcelable.Creator<ForecastDay>() {
        @Override
        public ForecastDay createFromParcel(Parcel source) {
            return new ForecastDay(source);
        }

        @Override
        public ForecastDay[] newArray(int size) {
            return new ForecastDay[size];
        }
    };
}
