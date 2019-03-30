package com.example.rishabh.unihub;

public class Note {

    String title,description,getKeyvalue;
    Long time;

    public Note() {
    }

    public Note(String title, String description, String getKeyvalue,Long time) {
        this.title = title;
        this.description = description;
        this.getKeyvalue = getKeyvalue;
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getGetKeyvalue() {
        return getKeyvalue;
    }

    public void setGetKeyvalue(String getKeyvalue) {
        this.getKeyvalue = getKeyvalue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
