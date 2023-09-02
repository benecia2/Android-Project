package com.example.blisgoproject.community;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Board  implements Serializable {
    private  Long id;
    private String title;
    private String name;
    private String content;
    private String category;
    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board(String title, String name, String content, String category) {
        this.title = title;
        this.name = name;
        this.content = content;
        this.category = category;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
