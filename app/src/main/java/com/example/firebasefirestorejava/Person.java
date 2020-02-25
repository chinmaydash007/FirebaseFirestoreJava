package com.example.firebasefirestorejava;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Person {
    String name;
    int priority;
    String uid;
    @ServerTimestamp
    Date date;

    public Person() {
    }

    public Person(String name, int priority) {
        this.name = name;
        this.priority = priority;


    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
