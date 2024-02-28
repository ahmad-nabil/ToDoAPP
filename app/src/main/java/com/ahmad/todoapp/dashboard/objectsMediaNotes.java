package com.ahmad.todoapp.dashboard;

public class objectsMediaNotes {
    String note;
    String Uri;

    public objectsMediaNotes(String note, String uri) {
        this.note = note;
        Uri = uri;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }
}
