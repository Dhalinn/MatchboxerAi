package com.example.matchboxer.dto;


public class MatchRequest {
    private int height;
    private int weight;
    private int reach;
    private String strength;
    private String weakness;

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getReach() { return reach; }
    public void setReach(int reach) { this.reach = reach; }

    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }

    public String getWeakness() { return weakness; }
    public void setWeakness(String weakness) { this.weakness = weakness; }
}



