package com.example.matchboxer.dto;

import java.util.List;


public class MatchResponse {
    private String boxer;
    private String record;
    private String titles;
    private String style;
    private String advice;
    private List<String> trainingPlan;

    public String getBoxer() { return boxer; }
    public void setBoxer(String boxer) { this.boxer = boxer; }

    public String getRecord() { return record; }
    public void setRecord(String record) { this.record = record; }

    public String getTitles() { return titles; }
    public void setTitles(String titles) { this.titles = titles; }

    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }

    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }

    public List<String> getTrainingPlan() { return trainingPlan; }
    public void setTrainingPlan(List<String> trainingPlan) { this.trainingPlan = trainingPlan; }
}
