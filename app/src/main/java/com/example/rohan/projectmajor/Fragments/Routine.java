package com.example.rohan.projectmajor.Fragments;

public class Routine {
    public String topic, timing;

    public Routine(String topic, String timing) {
        this.topic = topic;
        this.timing = timing;
    }
    public Routine(){}

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
