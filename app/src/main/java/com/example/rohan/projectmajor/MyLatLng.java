package com.example.rohan.projectmajor;

import android.os.Parcel;
import android.os.Parcelable;

public class MyLatLng implements Parcelable {

    String lat;
    String lng;

    public MyLatLng(String Lat, String Lng) {
        lat = Lat;
        lng = Lng;
    }
    public MyLatLng(){

    }

    protected MyLatLng(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<MyLatLng> CREATOR = new Creator<MyLatLng>() {
        @Override
        public MyLatLng createFromParcel(Parcel in) {
            return new MyLatLng(in);
        }

        @Override
        public MyLatLng[] newArray(int size) {
            return new MyLatLng[size];
        }
    };

    public String getlat() {
        return lat;
    }

    public void setlat(String Lat) {
        lat = Lat;
    }

    public String getlng() {
        return lng;
    }

    public void setlng(String Lng) {
        lng = Lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);

    }

    @Override
    public String toString() {
        return "MyLatLng{"+
                "lat='"+lat+'\''+
                ",lng='"+lng+'\''+
                "}";
    }
}
