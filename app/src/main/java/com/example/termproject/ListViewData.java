package com.example.termproject;

import java.sql.Blob;

public class ListViewData {
    private int id;
    private String date;
    private String category;
    private String content;
    private String address;
    private byte[]  camera;
    private byte[]  album;
    private String emotion;
    private int etime;
    private int edistance;

    public void setId(int id){this.id = id;}
    public void setDate(String date){this.date = date;}
    public void setCategory(String category){this.category = category;}
    public void setContent(String content){this.content = content;}
    public void setAddress(String address){this.address = address;}
    public void setCamera(byte[] camera){this.camera = camera;}
    public void setAlbum(byte[]  album){this.album = album;}
    public void setEmotion(String emotion){this.emotion = emotion;}
    public void setEtime(int etime){this.etime = etime;}
    public void setEdistance(int edistance){this.edistance = edistance;}

    public int getId(){return this.id;}
    public String getDate(){return this.date;}
    public String getCategory(){return this.category;}
    public String getContent(){return this.content;}
    public String getAddress(){return this.address;}
    public byte[]  getCamera(){return this.camera;}
    public byte[]  getAlbum(){return this.album;}
    public String getEmotion(){return this.emotion;}
    public int getEtime(){return this.etime;}
    public int getEdistance(){return this.edistance;}
}
