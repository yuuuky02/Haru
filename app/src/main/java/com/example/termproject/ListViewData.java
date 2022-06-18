package com.example.termproject;

public class ListViewData {
    private int id;
    private String date;
    private String category;
    private String content;
    private String address;
    private String camera;
    private String album;
    private String emotion;

    public void setId(int id){this.id = id;}
    public void setDate(String date){this.date = date;}
    public void setCategory(String category){this.category = category;}
    public void setContent(String content){this.content = content;}
    public void setAddress(String address){this.address = address;}
    public void setCamera(String camera){this.camera = camera;}
    public void setAlbum(String album){this.album = album;}
    public void setEmotion(String emotion){this.emotion = emotion;}

    public int getId(){return this.id;}
    public String getDate(){return this.date;}
    public String getCategory(){return this.category;}
    public String getContent(){return this.content;}
    public String getAddress(){return this.address;}
    public String getCamera(){return this.camera;}
    public String getAlbum(){return this.album;}
    public String getEmotion(){return this.emotion;}
}
