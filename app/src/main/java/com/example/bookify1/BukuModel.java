package com.example.bookify1;

public class BukuModel {
    String title, writer, desc, year, dataImage, key;
    public BukuModel(String title, String writer, String desc, String year, String dataImage) {
        this.title = title;
        this.writer = writer;
        this.desc = desc;
        this.year = year;
        this.dataImage = dataImage;
    }

    public BukuModel(){

    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getDesc() {
        return desc;
    }

    public String getYear() {
        return year;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() { return key;
    }
}
