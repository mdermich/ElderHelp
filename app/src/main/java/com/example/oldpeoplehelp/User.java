package com.example.oldpeoplehelp;

public class User {

    public String fullname,email,password,sexe,address,age,dateOfBirth,image,status;

    public User(){

    }
    public User(String fullname,String email,String password,String sexe,String address,String age,String dateOfBirth){
        //this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.address = address;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
    }
    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
