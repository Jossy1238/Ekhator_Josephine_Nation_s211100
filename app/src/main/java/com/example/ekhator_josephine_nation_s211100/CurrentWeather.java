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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentWeather {


    private String minimumTemperature;

    private  String condition;

    private  String date;
    private String city;
    private String maximumTemperature;
    private String windDirection;
    private String windSpeed;
    private String visibility;
    private String pressure;
    private String humidity;
    private String uvRisk;
    private String pollution;
    private String sunrise;
    private String sunset;
    private String location;

    public CurrentWeather(){};


    public CurrentWeather(String minimumTemperature, String condition, String date, String city, String maximumTemperature, String windDirection, String windSpeed, String visibility, String pressure, String humidity, String uvRisk, String pollution, String sunrise, String sunset, String location) {
        this.minimumTemperature = minimumTemperature;
        this.condition = condition;
        this.date = date;
        this.city = city;
        this.maximumTemperature = maximumTemperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvRisk = uvRisk;
        this.pollution = pollution;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.location = location;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(String minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(String maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "minimumTemperature='" + minimumTemperature + '\'' +
                ", condition='" + condition + '\'' +
                ", date='" + date + '\'' +
                ", city='" + city + '\'' +
                ", maximumTemperature='" + maximumTemperature + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", uvRisk='" + uvRisk + '\'' +
                ", pollution='" + pollution + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public void parseString(String data) {
        // Split the string based on the format used in toString()
        String[] parts = data.split(", ");

        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                // Set the corresponding attribute based on the key
                switch (key) {
                    case "minimumTemperature":
                        minimumTemperature = value;
                        break;
                    case "maximumTemperature":
                        maximumTemperature = value;
                        break;
                    case "windDirection":
                        windDirection = value;
                        break;
                    case "windSpeed":
                        windSpeed = value;
                        break;
                    case "visibility":
                        visibility = value;
                        break;
                    case "pressure":
                        pressure = value;
                        break;
                    case "humidity":
                        humidity = value;
                        break;
                    case "uvRisk":
                        uvRisk = value;
                        break;
                    case "pollution":
                        pollution = value;
                        break;
                    case "sunrise":
                        sunrise = value;
                        break;
                    case "sunset":
                        sunset = value;
                        break;
                    case "location":
                        location = value;
                        break;
                }
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equals("title")) {
                        // Parse location from title tag
                        String title = parser.nextText();
                        location = title.substring(title.lastIndexOf("-") + 1).trim();
                    } else if (tagName.equals("georss:point")) {
                        location = parser.nextText();
                    } else if (tagName.equals("description")) {
                        parseDescription(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    // Handle end tags if needed
                    break;
            }
            eventType = parser.next();
        }
    }

    public void parseDescription(String description) {

        // For demonstration, let's assume the description format is consistent:
        String[] parts = description.split(", ");
        for (String part : parts) {
            if (part.contains("Minimum Temperature:")) {
                minimumTemperature = part.split(": ")[1];
            } else if (part.contains("Maximum Temperature:")) {

                maximumTemperature = part.split(": ")[1];
            } else if (part.contains("Wind Direction:")) {
                windDirection = part.split(": ")[1];
            } else if (part.contains("Wind Speed:")) {
                windSpeed = part.split(": ")[1];
            } else if (part.contains("Visibility:")) {
                visibility = part.split(": ")[1];
            } else if (part.contains("Pressure:")) {
                pressure = part.split(": ")[1];
            } else if (part.contains("Humidity:")) {
                humidity = part.split(": ")[1];
            } else if (part.contains("UV Risk:")) {
                uvRisk = part.split(": ")[1];
            } else if (part.contains("Pollution:")) {
                pollution = part.split(": ")[1];
            } else if (part.contains("Sunrise:")) {
                sunrise = part.split(": ")[1];
            } else if (part.contains("Sunset:")) {
                sunset = part.split(": ")[1];
            }
        }
    }

    // Method to cache weather data
    public static void cacheWeatherData(Context context, List<CurrentWeather> weatherList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WeatherCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear existing cached weather data
        editor.clear();

        // Store the size of the weather list for later retrieval
        editor.putInt("weatherListSize", weatherList.size());

        // Iterate over the weather list and store each weather object
        for (int i = 0; i < weatherList.size(); i++) {
            CurrentWeather currentWeather = weatherList.get(i);
            editor.putString("minimumTemperature" + i, currentWeather.getMinimumTemperature());
            editor.putString("maximumTemperature" + i, currentWeather.getMaximumTemperature());
            editor.putString("windDirection" + i, currentWeather.getWindDirection());
            editor.putString("windSpeed" + i, currentWeather.getWindSpeed());
            editor.putString("visibility" + i, currentWeather.getVisibility());
            editor.putString("pressure" + i, currentWeather.getPressure());
            editor.putString("humidity" + i, currentWeather.getHumidity());
            editor.putString("uvRisk" + i, currentWeather.getUvRisk());
            editor.putString("pollution" + i, currentWeather.getPollution());
            editor.putString("sunrise" + i, currentWeather.getSunrise());
            editor.putString("sunset" + i, currentWeather.getSunset());
            editor.putString("location" + i, currentWeather.getLocation());
            editor.putString("condition" + i, currentWeather.getCondition()); // Include condition variable
            editor.putString("date" + i, currentWeather.getDate()); // Include date variable
            editor.putString("city" + i, currentWeather.getCity()); // Include city variable
        }

        // Apply the changes
        editor.apply();
    }

    // Method to load cached weather data
    public static List<CurrentWeather> loadCachedWeatherData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WeatherCache", Context.MODE_PRIVATE);
        List<CurrentWeather> weatherList = new ArrayList<>();

        // Retrieve the size of the weather list
        int weatherListSize = sharedPreferences.getInt("weatherListSize", 0);

        // Iterate over the stored weather data and reconstruct the weather list
        for (int i = 0; i < weatherListSize; i++) {
            String minimumTemperature = sharedPreferences.getString("minimumTemperature" + i, "");
            String maximumTemperature = sharedPreferences.getString("maximumTemperature" + i, "");
            String windDirection = sharedPreferences.getString("windDirection" + i, "");
            String windSpeed = sharedPreferences.getString("windSpeed" + i, "");
            String visibility = sharedPreferences.getString("visibility" + i, "");
            String pressure = sharedPreferences.getString("pressure" + i, "");
            String humidity = sharedPreferences.getString("humidity" + i, "");
            String uvRisk = sharedPreferences.getString("uvRisk" + i, "");
            String pollution = sharedPreferences.getString("pollution" + i, "");
            String sunrise = sharedPreferences.getString("sunrise" + i, "");
            String sunset = sharedPreferences.getString("sunset" + i, "");
            String location = sharedPreferences.getString("location" + i, "");
            String condition = sharedPreferences.getString("condition" + i, ""); // Retrieve condition variable
            String date = sharedPreferences.getString("date" + i, ""); // Retrieve date variable
            String city = sharedPreferences.getString("city" + i, ""); // Retrieve city variable

            // Create CurrentWeather object and add it to the list
            CurrentWeather currentWeather = new CurrentWeather(minimumTemperature, "", "", "", maximumTemperature, windDirection, windSpeed, visibility, pressure, humidity, uvRisk, pollution, sunrise, sunset, location);
            currentWeather.setCondition(condition); // Set condition variable
            currentWeather.setDate(date); // Set date variable
            currentWeather.setCity(city); // Set city variable
            weatherList.add(currentWeather);
        }

        return weatherList;
    }


    public List<CurrentWeather> parseXMLFromFuture(Context context, String urlLink) {
        List<CurrentWeather> currentWeathers = new ArrayList<>();

        try {
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

            currentWeathers = parseXMLFuture(parser);
            cacheWeatherData(context,currentWeathers);
            Log.d("CVFx",currentWeathers.toString());

            stream.close();
            conn.disconnect();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            // Handle network-related exceptions

            // Load data from cache if network request fails
            currentWeathers = loadCachedWeatherData(context);
            Log.d("CVF",currentWeathers.toString());
        }

        return currentWeathers;
    }


    private List<CurrentWeather> parseXMLFuture(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<CurrentWeather> currentWeathers = new ArrayList<>();
        int eventType = parser.getEventType();
        CurrentWeather currentWeather = null;
        int currentWeatherCount = 0;

        String city = null;
        String date = null;
        String condition = null;

        while (eventType != XmlPullParser.END_DOCUMENT && currentWeatherCount < 3) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equals("item")) {
                        currentWeather = new CurrentWeather();
                    } else if (tagName.equals("title")) {
                        String title = parser.nextText();

                        if (title.contains(":")) {
                            condition = title.split(",")[0].split(":")[1];
                            date = title.split(",")[0].split(":")[0];



                        } else {
                            // Extract city from title of <item>
                            city= title.substring(title.indexOf(":") + 1).trim();
                            // Regular expression to match the city name pattern
                            String regex = "Forecast for\\s+(.*?),";
                            // Compile the regular expression
                            Pattern pattern = Pattern.compile(regex);

                            // Match the pattern against the input string
                            Matcher matcher = pattern.matcher(city);

                            // Check if the pattern is found
                            if (matcher.find()) {
                                // Extract the city name
                                city = matcher.group(1);

                            } else {
                                city=null;
                            }
                        }
                    } else if (tagName.equals("description") && currentWeather != null) {
                        currentWeather.parseDescription(parser.nextText());
                    } else if (tagName.equals("georss:point")) {
                        // Extract location from <georss:point>
                        if (currentWeather != null) {
                            currentWeather.setLocation(parser.nextText());
                        } else {
                            // Handle city extraction from <image> tag
                            if (city == null) {
                                city = parser.nextText();
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (tagName.equals("item") && currentWeather != null) {
                        // Set city and date to the current weather object
                        currentWeather.setCity(city);
                        currentWeather.setDate(date);
                        currentWeather.setCondition(condition);
                        currentWeathers.add(currentWeather);
                        currentWeatherCount++;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return currentWeathers;
    }


}

