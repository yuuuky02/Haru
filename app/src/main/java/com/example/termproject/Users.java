package com.example.termproject;

public class Users {
    public int id;
    public String userName;
    public int userId;
    public int userPassword;

    public void setId(int id){this.id = id;}
    public void setUserName(String userName){this.userName = userName;}
    public void setUserId(int userId){this.userId = userId;}
    public void setUserPassword(int userPassword){this.userPassword = userPassword;}

    public int getId(){return this.id;}
    public String getUserName(){return this.userName;}
    public int getUserId(){return this.userId;}
    public int getUserPassword(){return this.userPassword;}
}
