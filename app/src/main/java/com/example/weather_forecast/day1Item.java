package com.example.weather_forecast;

public class day1Item {
    private String Icon;
    private String Max;
    private String Min;
    private String Now;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNow() {
        return Now;
    }

    public void setNow(String now) {
        Now = now;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

}
