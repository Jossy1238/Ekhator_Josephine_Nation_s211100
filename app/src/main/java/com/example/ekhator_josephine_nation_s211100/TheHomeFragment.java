
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

import static com.example.ekhator_josephine_nation_s211100.FetchWeatherTask.getCityUrlByName;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TheHomeFragment extends Fragment {

    private TextView city_name;
    private TextView weather_condition;
    private TextView text_date_time;
    private TextView text_current_temp;
    private ImageView sun_icon;
    private TextView text_sunrise;
    private TextView text_sunset;
    private TextView uv_risk;
    private TextView temp_min;
    private TextView temp_max;
    private TextView visibility;
    private TextView text_pressure;
    private TextView text_humidity;
    private TextView text_wind;

    // new for 3 days forecast fields
    private TextView first_day_condition;
    private TextView forecast_today;
    private TextView forecast_firstday_label;
    private TextView second_day_condition;
    private TextView forecast_tomorrow;
    private TextView forecast_secondday_label;
    private TextView third_day_condition;
    private TextView forecast_dayAfterTomorrow;
    private TextView forecast_thirdday_label;

    private ImageView forecast_today_icon;
    private ImageView forecast_tomorrow_icon;
    private ImageView forecast_dayAfterTomorrow_icon;


    List<CurrentWeather> weather;
    Calendar currentDate = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
    String formattedDate = formatter.format(currentDate.getTime());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_home, container, false);

        LinearLayout todayLinear = view.findViewById(R.id.todayLinear);
        LinearLayout tommorrowLinear = view.findViewById(R.id.tommorrowLinear);
        LinearLayout futureLinear = view.findViewById(R.id.futureLinear);

        // Find views
        city_name = view.findViewById(R.id.city_name);
        weather_condition = view.findViewById(R.id.weather_condition);
        text_date_time = view.findViewById(R.id.text_date_time);
        text_current_temp = view.findViewById(R.id.text_current_temp);
        sun_icon = view.findViewById(R.id.sun_icon);
        text_sunrise = view.findViewById(R.id.text_sunrise);
        text_sunset = view.findViewById(R.id.text_sunset);
        uv_risk = view.findViewById(R.id.uv_risk);
        temp_min = view.findViewById(R.id.temp_min);
        temp_max = view.findViewById(R.id.temp_max);
        visibility = view.findViewById(R.id.visibility);
        text_pressure = view.findViewById(R.id.text_pressure);
        text_humidity = view.findViewById(R.id.text_humidity);
        text_wind = view.findViewById(R.id.text_wind);

        // For 3 days forecast fields
        // First day
        first_day_condition = view.findViewById(R.id.first_day_condition);
        forecast_today = view.findViewById(R.id.forecast_today);
        forecast_firstday_label = view.findViewById(R.id.forecast_firstday_label);

        // Second day
        second_day_condition = view.findViewById(R.id.second_day_condition);
        forecast_tomorrow = view.findViewById(R.id.forecast_tomorrow);
        forecast_secondday_label = view.findViewById(R.id.forecast_secondday_label);

        // Third day
        third_day_condition = view.findViewById(R.id.third_day_condition);
        forecast_dayAfterTomorrow = view.findViewById(R.id.forecast_dayAfterTomorrow);
        forecast_thirdday_label = view.findViewById(R.id.forecast_thirdday_label);

        //Display Icon for three days
        forecast_today_icon = view.findViewById(R.id.forecast_today_icon);
        forecast_tomorrow_icon = view.findViewById(R.id.forecast_tomorrow_icon);
        forecast_dayAfterTomorrow_icon = view.findViewById(R.id.forecast_dayAfterTomorrow_icon);


        // Fetch selected city name and URL from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SettingsFragment.PREF_FILE_NAME, Context.MODE_PRIVATE);
        String defaultCityUrl = sharedPreferences.getString(SettingsFragment.CAMPUS_KEY,  "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
        Log.d("SettingsFragment", defaultCityUrl);

        // Get URL from arguments, use default if null
        String urlLink = getArguments() != null ? getArguments().getString("url") :
                defaultCityUrl;

        // Fetch weather data
        fetchWeatherData(urlLink);

        todayLinear.setOnClickListener(v -> populateMainLinearWithForecastDetails(weather.get(0)) );
        tommorrowLinear.setOnClickListener(v -> populateMainLinearWithForecastDetails(weather.get(1)) );
        futureLinear.setOnClickListener(v -> populateMainLinearWithForecastDetails(weather.get(2)) );

        return view;
    }

    private void fetchWeatherData(String urlLink) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                // Fetch weather data using FetchWeatherTask
                weather= FetchWeatherTask.fetchWeatherData(getContext(), urlLink);

                getActivity().runOnUiThread(() -> {
                    if (weather != null) {

                        // Populate UI with weather data
                        populateForecasts(weather );
                        populateMainLinearWithForecastDetails(weather.get(0));
                    } else {
                        // Handle case where weather data is not available
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        });
    }


    private void populateForecasts(List<CurrentWeather> weatherList) {
        if (weatherList == null) {
            // Set placeholder values or display loading message
            first_day_condition.setText("Loading...");
            forecast_today.setText("Loading...");
            forecast_firstday_label.setText("Loading...");

            second_day_condition.setText("Loading...");
            forecast_tomorrow.setText("Loading...");
            forecast_secondday_label.setText("Loading...");

            third_day_condition.setText("Loading...");
            forecast_dayAfterTomorrow.setText("Loading...");
            forecast_thirdday_label.setText("Loading...");
        } else {

            // Populate forecasts with actual weather data
            CurrentWeather today = weatherList.get(0);
            CurrentWeather tomorrow = weatherList.get(1);
            CurrentWeather future = weatherList.get(2);

            first_day_condition.setText(today.getCondition());
            forecast_today.setText(today.getMinimumTemperature());
            forecast_firstday_label.setText(today.getDate());
            setForecastIcon(today.getCondition(), forecast_today_icon);

            second_day_condition.setText(tomorrow.getCondition());
            forecast_tomorrow.setText(tomorrow.getMinimumTemperature());
            forecast_secondday_label.setText(tomorrow.getDate());
            setForecastIcon(tomorrow.getCondition(), forecast_tomorrow_icon);

            third_day_condition.setText(future.getCondition());
            forecast_dayAfterTomorrow.setText(future.getMinimumTemperature());
            forecast_thirdday_label.setText(future.getDate());
            setForecastIcon(future.getCondition(), forecast_dayAfterTomorrow_icon);

        }
    }

    private void populateMainLinearWithForecastDetails(CurrentWeather forecast) {
        if (forecast == null) {
            // Set placeholder values or display loading message
            city_name.setText("Loading...");
            text_date_time.setText("Loading...");
            text_current_temp.setText("Loading...");
            text_sunrise.setText("Loading...");
            text_sunset.setText("Loading...");
            uv_risk.setText("Loading...");
            temp_min.setText("Loading...");
            temp_max.setText("Loading...");
            visibility.setText("Loading...");
            text_pressure.setText("Loading...");
            text_humidity.setText("Loading...");
            text_wind.setText("Loading...");
        } else {
            // Populate UI components with forecast details
            city_name.setText(forecast.getCity());
            text_date_time.setText(forecast.getDate());
            text_current_temp.setText(forecast.getMaximumTemperature());
            text_sunrise.setText(forecast.getSunrise());
            text_sunset.setText(forecast.getSunset());
            uv_risk.setText(forecast.getUvRisk());
            temp_min.setText(forecast.getMinimumTemperature());
            temp_max.setText(forecast.getMaximumTemperature());
            visibility.setText(forecast.getVisibility());
            text_pressure.setText(forecast.getPressure());
            text_humidity.setText(forecast.getHumidity());
            text_wind.setText(forecast.getWindSpeed());

            if (forecast.getDate().equals("Today") || forecast.getDate().equals("Tonight")) {
                // If the date is "Today" or "Tonight"
                text_date_time.setText(formattedDate.toString());
            }

            // Set weather condition text
            weather_condition.setText(forecast.getCondition());

            // Set weather icon based on condition
            setWeatherIcon(forecast.getCondition());

        }
    }

    // Set the weather icon based on the weather condition
    private void setWeatherIcon(String condition) {
        int iconResource;
        String lowerCaseCondition = condition.toLowerCase();

        if (lowerCaseCondition.contains("clear sky")) {
            iconResource = R.drawable.day_clear;
        } else if (lowerCaseCondition.contains("sunny")) {
            iconResource = R.drawable.day_clear;
        } else if (lowerCaseCondition.contains("partly cloudy")) {
            iconResource = R.drawable.day_partial_cloud;
        } else if (lowerCaseCondition.contains("cloudy")) {
            iconResource = R.drawable.cloudy;
        } else if (lowerCaseCondition.contains("overcast")) {
            iconResource = R.drawable.overcast;
        } else if (lowerCaseCondition.contains("light rain")) {
            iconResource = R.drawable.day_rain;
        } else if (lowerCaseCondition.contains("light rain showers")) {
            iconResource = R.drawable.rain_thunder;
        } else if (lowerCaseCondition.contains("moderate rain")) {
            iconResource = R.drawable.rain;
        } else if (lowerCaseCondition.contains("thunderstorms")) {
            iconResource = R.drawable.day_snow_thunder;
        } else if (lowerCaseCondition.contains("snow")) {
            iconResource = R.drawable.snow;
        } else if (lowerCaseCondition.contains("light snow")) {
            iconResource = R.drawable.day_snow;
        } else if (lowerCaseCondition.contains("heavy snow")) {
            iconResource = R.drawable.snow_thunder;
        } else if (lowerCaseCondition.contains("fog")) {
            iconResource = R.drawable.fog;
        } else if (lowerCaseCondition.contains("tornado")) {
            iconResource = R.drawable.tornado;
        } else {
            iconResource = R.drawable.splashscreen; // Default icon if condition is not recognized
        }

        sun_icon.setImageResource(iconResource);
    }

    // Set the forecast icon based on the weather condition
    private void setForecastIcon(String condition, ImageView forecastIcon) {
        int iconResource;
        String lowerCaseCondition = condition.toLowerCase();

        if (lowerCaseCondition.contains("clear sky") || lowerCaseCondition.contains("sunny")) {
            iconResource = R.drawable.day_clear;
        } else if (lowerCaseCondition.contains("partly cloudy")) {
            iconResource = R.drawable.day_partial_cloud;
        } else if (lowerCaseCondition.contains("cloudy")) {
            iconResource = R.drawable.cloudy;
        } else if (lowerCaseCondition.contains("overcast")) {
            iconResource = R.drawable.overcast;
        } else if (lowerCaseCondition.contains("light rain") || lowerCaseCondition.contains("light rain showers")) {
            iconResource = R.drawable.day_rain;
        } else if (lowerCaseCondition.contains("moderate rain")) {
            iconResource = R.drawable.rain;
        } else if (lowerCaseCondition.contains("thunderstorms")) {
            iconResource = R.drawable.day_snow_thunder;
        } else if (lowerCaseCondition.contains("snow") || lowerCaseCondition.contains("light snow") || lowerCaseCondition.contains("heavy snow")) {
            iconResource = R.drawable.snow;
        } else if (lowerCaseCondition.contains("fog")) {
            iconResource = R.drawable.fog;
        } else if (lowerCaseCondition.contains("tornado")) {
            iconResource = R.drawable.tornado;
        } else {
            iconResource = R.drawable.splashscreen; // Default icon if condition is not recognized
        }

        forecastIcon.setImageResource(iconResource);
    }

}



