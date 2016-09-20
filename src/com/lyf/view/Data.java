package com.lyf.view;

public class Data {
    private String name;
    private String data;

    public Data(){

    }

    public Data(String name, String data){
        this.name = name;
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdata(String data) {
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }
}

