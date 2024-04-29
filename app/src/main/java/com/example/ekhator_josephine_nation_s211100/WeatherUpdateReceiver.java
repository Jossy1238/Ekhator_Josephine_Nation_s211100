/*  Starter project for Mobile Platform Development in main diet 2023/2024
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Josephine Nation Ekhator
// Student ID           s211100
// Programme of Study   Computing
//
package com.example.ekhator_josephine_nation_s211100;

import static com.example.ekhator_josephine_nation_s211100.FetchWeatherTask.getCityIdByName;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WeatherUpdateReceiver extends BroadcastReceiver {

    public static final String TAG = "WeatherUpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Weather update received");

        // Get city name from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("WeatherPrefs", Context.MODE_PRIVATE);
        String cityName = sharedPreferences.getString("CityName", "London");

        // Get city ID from city name
        String cityId = getCityIdByName(cityName);


        // Construct URL with city ID
        String url = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + cityId;
        fetchWeatherData(context, url);
    }

    private void fetchWeatherData(Context context, String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<MapWeatherData> future = executor.submit(() -> {
            CurrentWeather currentWeather = FetchWeatherTask.parseXMLFromURL(url);
            if (currentWeather != null) {
                return FetchWeatherTask.getMapWeatherData(currentWeather);
            }
            return null;
        });

        try {
            MapWeatherData mapWeatherData = future.get();
            if (mapWeatherData != null) {
                // Display received weather data in the notification
                String location = mapWeatherData.getLocation();
                String notificationContent =
                        "Min Temp: " + mapWeatherData.getMinimumTemperature() + "\n" +
                                "Max Temp: " + mapWeatherData.getMaximumTemperature() + "\n" +
                                "Pressure: " + mapWeatherData.getPressure() + "\n" +
                                "Humidity: " + mapWeatherData.getHumidity();

                // Send notification
                NotificationUtils.createNotificationChannel(context);
                NotificationUtils.showNotification(context, "Weather Update", notificationContent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching weather data", e);
        }

        executor.shutdown();
    }
}
