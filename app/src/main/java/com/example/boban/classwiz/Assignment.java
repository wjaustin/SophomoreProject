package com.example.boban.classwiz;

/**
 * Created by Boban on 12/8/2014.
 */
public class Assignment {
    String title;
    String desc;

    public Assignment(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String temp){
        title = temp;
    }

    public void setDesc(String temp){
        desc = temp;
    }
}
