package com.example.mobiholterfinal.model;

public class UroData {

    private float time;
    private float weight;
    private float title;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getTitle() {
        return title;
    }

    public void setTitle(float title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UroData{" +
                "time=" + time +
                ", weight=" + weight +
                ", title=" + title +
                '}';
    }
}
