package com.example.myapplication.knowledge;

import java.util.List;

public class Knowledge {
    public int code;
    public String msg;
    public List<NewslistDTO> newslist;

    public static class NewslistDTO {
        public String title;
        public String content;
    }
}
