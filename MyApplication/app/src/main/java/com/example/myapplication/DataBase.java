package com.example.myapplication;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "DataBase")
public class DataBase {
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;

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
}
