package com.bitbytelab.app_227.Models;

public class Student {
    String name;
    String reg;
    String id;

    public Student() {
    }

    public Student(String id, String name, String reg){
        this.name = name;
        this.reg = reg;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
