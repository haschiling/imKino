package com.example.imkino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Item {

    private byte[] image;
    private String name;
    private String genre;
    private String year;





    public Item(byte[] image, String name, String genre, String year) {

        this.image = image;
        this.name = name;
        this.genre = genre;
        this.year = year;

    }





    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public  String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public  String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}