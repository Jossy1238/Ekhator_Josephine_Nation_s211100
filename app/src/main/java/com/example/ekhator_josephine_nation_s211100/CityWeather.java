package com.example.ekhator_josephine_nation_s211100;

public class CityWeather {
    private CurrentWeather currentWeather;
    private String url;

    public CityWeather(CurrentWeather currentWeather, String url) {
        this.currentWeather = currentWeather;
        this.url = url;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public String getUrl() {
        return url;
    }
}
