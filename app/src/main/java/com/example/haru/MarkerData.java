package com.example.haru;

public class MarkerData {
    public int id;
    public String name;
    public String latitude;
    public String longitude;

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setLatitude(String latitude){this.latitude = latitude;}
    public void setLongitude(String longitude){this.longitude = longitude;}

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getLatitude(){return this.latitude;}
    public String getLongitude(){return this.longitude;}
}
