package com.example.halp;

import java.text.SimpleDateFormat;

public class Order {
    private String quest_id;
    private SimpleDateFormat date;
    private int people;
    private float cost;
    private String status;

    public String getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(String quest_id) {
        this.quest_id = quest_id;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
