package com.ahmad.todoapp.todo;

public class objectModel {
   String note;
String img;

    public objectModel() {
    }

    public objectModel(String note, String img) {
        this.note = note;
        this.img = img;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
