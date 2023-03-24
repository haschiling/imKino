package com.example.imkino;

public class FILMS {
    private byte[] image;
    String name;
    String genre;
    String year;
    String top;

    public FILMS(byte[] image, String name, String genre, String year, String top) {
        this.image = image;
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.top = top;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }


}


