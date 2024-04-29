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

import static com.example.ekhator_josephine_nation_s211100.CurrentWeather.loadCachedWeatherData;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FetchWeatherTask {

    public static List<CurrentWeather> fetchWeatherData(Context context, String urlLink) {
        try {
            // Parse weather data from the XML URL
            CurrentWeather current = new CurrentWeather();
            List<CurrentWeather> currentWeathers = current.parseXMLFromFuture(context,urlLink);

            if (currentWeathers.isEmpty()) {
                // If parsed data is empty, load cached weather data
                return CurrentWeather.loadCachedWeatherData(context);
            } else {
                // Cache the weather data if new data is fetched
                CurrentWeather.cacheWeatherData(context, currentWeathers);
                return currentWeathers;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // If an exception occurs during parsing or network request, load cached weather data
            return CurrentWeather.loadCachedWeatherData(context);
        }
    }



    static CurrentWeather parseXMLFromURL(String urlLink) throws IOException, XmlPullParserException {
        URL url = new URL(urlLink);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();

        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser parser = xmlFactoryObject.newPullParser();

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(stream, null);

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.parseXML(parser);

        stream.close();
        conn.disconnect();


        return currentWeather;
    }

    public static MapWeatherData getMapWeatherData(CurrentWeather weather) {
        MapWeatherData mapWeatherData = new MapWeatherData();

        // Splitting location into latitude and longitude
        String[] locationParts = weather.getLocation().split(" ");
        mapWeatherData.setLatitude(locationParts[0]);
        mapWeatherData.setLongitude(locationParts[1]);

        mapWeatherData.setLocation(weather.getLocation());
        mapWeatherData.setMinimumTemperature(weather.getMinimumTemperature());
        mapWeatherData.setMaximumTemperature(weather.getMaximumTemperature());
        mapWeatherData.setPressure(weather.getPressure());
        mapWeatherData.setHumidity(weather.getHumidity());

        return mapWeatherData;
    }

    public static List<MapWeatherData> convertToMapWeatherData(List<String> urls) {
        List<MapWeatherData> mapWeatherDataList = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(urls.size());
        List<Future<CurrentWeather>> futures = new ArrayList<>();

        // Fetch weather data for each URL in parallel
        for (final String url : urls) {
            Future<CurrentWeather> future = executor.submit(() -> parseXMLFromURL(url));
            futures.add(future);
        }

        // Convert each weather data object to map data object
        for (Future<CurrentWeather> future : futures) {
            try {
                CurrentWeather currentWeather = future.get();
                if (currentWeather != null) {
                    MapWeatherData mapWeatherData = getMapWeatherData(currentWeather);
                    mapWeatherDataList.add(mapWeatherData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Shutdown executor
        executor.shutdown();

        return mapWeatherDataList;
    }

    public static String getCityIdByName(String cityName) {
        if ("London".equals(cityName)) {
            return "2643743";
        } else if ("Glasgow".equals(cityName)) {
            return "2648579";
        } else if ("New York".equals(cityName)) {
            return "5128581";
        } else if ("Oman".equals(cityName)) {
            return "287286";
        } else if ("Mauritius".equals(cityName)) {
            return "934154";
        } else if ("Bangladesh".equals(cityName)) {
            return "1185241";
        } else {
            return "Unknown";
        }
    }

    public static String getCityUrlByName(String cityName) {
        if ("London".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
        } else if ("Glasgow".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
        } else if ("New York".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
        } else if ("Oman".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
        } else if ("Mauritius".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
        } else if ("Bangladesh".equals(cityName)) {
            return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
        } else {
            return null;
        }
    }



}


