package com.example.myapplication.home;

import java.util.List;

public class Season {
    public int code;
    public String msg;
    public List<ListDTO> list;

    public static class ListDTO {
        public String title;
        public String content1;
        public String content2;
        public String content3;
        public Object content4;
        public String type;
    }
}
