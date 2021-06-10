package com.mobcom.weatherapps.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hour implements Parcelable {
    private String time;
    private double temp_c;
    private ConditionModel condition;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        time = time;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        temp_c = temp_c;
    }

    public ConditionModel getCondition() {
        return condition;
    }

    public void setCondition(ConditionModel condition) {
        this.condition = condition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeDouble(this.temp_c);
        dest.writeParcelable(this.condition, flags);
    }

    public void readFromParcel(Parcel source) {
        this.time = source.readString();
        this.temp_c = source.readDouble();
        this.condition = source.readParcelable(ConditionModel.class.getClassLoader());
    }

    public Hour() {
    }

    protected Hour(Parcel in) {
        this.time = in.readString();
        this.temp_c = in.readDouble();
        this.condition = in.readParcelable(ConditionModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<Hour> CREATOR = new Parcelable.Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}
