package com.example.imkino;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Upload implements Serializable {
    private String name;
    private String genre;
    private String year;
    private String limit;
    private String about;
    private String imageUri;
    private String mKey;

    public Upload(){

    }

    public Upload(String Mname,String Mgenre,String Myear, String Mlimit,String Mabout,String MimageUri){
        if(Mname.trim().equals("")){
            Mname = "No name";
        }

        name = Mname;
        genre = Mgenre;
        year = Myear;
        limit = Mlimit;
        about = Mabout;
        imageUri = MimageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
