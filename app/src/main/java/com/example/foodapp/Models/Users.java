package com.example.foodapp.Models;

public class Users {
    String profilePic,name,mail,password,userId;

    public Users(String profilePic, String name, String mail, String password, String userId) {
        this.profilePic = profilePic;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
    }

    public Users()
    {

    }

    // SignUp Constructor

    public Users(String name, String mail, String password) {

        this.name = name;
        this.mail = mail;
        this.password = password;

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
