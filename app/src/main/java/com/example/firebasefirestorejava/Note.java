package com.example.firebasefirestorejava;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.google.gson.annotations.Expose;

public class Note {
    String name;
    String number;
    String uid;
    int priority;

    public Note(String name, String number,int priority) {
        this.name = name;
        this.number = number;
        this.priority=priority;
    }
    public int getPriority() {
        return priority;
    }

    public Note() {
    }
    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
