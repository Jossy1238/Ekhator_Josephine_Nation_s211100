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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.util.Log;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private static List<CityWeather> cityWeathers;
    private OnItemClickListener onItemClickListener;

    public CityAdapter(List<CityWeather> cityWeathers, OnItemClickListener listener) {
        this.cityWeathers = cityWeathers;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_weather, parent, false);
        return new CityViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityWeather cityWeather = cityWeathers.get(position);
        holder.bind(cityWeather);
    }

    @Override
    public int getItemCount() {
        return cityWeathers.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView cityNameTextView;
        public TextView weatherConditionTextView;
        public TextView minMaxTemperatureTextView;
        public ImageView weatherIconImageView;

        private OnItemClickListener onItemClickListener;

        public CityViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cityNameTextView = itemView.findViewById(R.id.cityNameTextView);
            weatherConditionTextView = itemView.findViewById(R.id.weatherConditionTextView);
            minMaxTemperatureTextView = itemView.findViewById(R.id.minMaxTemperatureTextView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
            this.onItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        public void bind(CityWeather cityWeather) {
            CurrentWeather weather = cityWeather.getCurrentWeather();
            cityNameTextView.setText(weather.getCity());
            weatherConditionTextView.setText(weather.getCondition());
            minMaxTemperatureTextView.setText(weather.getMinimumTemperature());

            // Set the weather icon based on the weather condition
            setWeatherIcon(weather.getCondition());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position, cityWeathers.get(position));
                }
            }
        }

        private void setWeatherIcon(String condition) {
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

            weatherIconImageView.setImageResource(iconResource);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CityWeather cityWeather);
    }
}




