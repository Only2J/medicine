package com.example.myapplication;

public class Video {
    private String name;
    private int imageId;
    private String url;

    public Video(String name, int imageId, String url) {
        this.name = name;
        this.imageId = imageId;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
