package com.example.rohan.projectmajor;

public class MyLatLng {
    String lat;
    String lng;

    public MyLatLng(String Lat, String Lng) {
        lat = Lat;
        lng = Lng;
    }
    public MyLatLng(){

    }

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
}
