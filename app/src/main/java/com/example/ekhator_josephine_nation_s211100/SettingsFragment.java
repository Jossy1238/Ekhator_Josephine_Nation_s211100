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

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ekhator_josephine_nation_s211100.FetchWeatherTask.getCityUrlByName;
import static com.example.ekhator_josephine_nation_s211100.NotificationUtils.showNotification;
import static java.util.Collections.addAll;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFragment extends Fragment {

    public static final String PREF_FILE_NAME = "MyPrefs";
    public static final String MORNING_UPDATE_TIME_KEY = "morning_update_time";
    public static final String EVENING_UPDATE_TIME_KEY = "evening_update_time";
    public static final String CAMPUS_KEY = "campus";
    public static final String THEME_KEY = "theme";

    private EditText morningUpdateEditText;
    private EditText eveningUpdateEditText;
    private Spinner campusSpinner;
    private Spinner themeSpinner;
    private Button saveSettingsButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        morningUpdateEditText = rootView.findViewById(R.id.editTextMorningUpdate);
        eveningUpdateEditText = rootView.findViewById(R.id.editTextEveningUpdate);
        campusSpinner = rootView.findViewById(R.id.spinnerCampusOptions);
        themeSpinner = rootView.findViewById(R.id.spinnerThemeOptions);
        saveSettingsButton = rootView.findViewById(R.id.buttonSaveSettings);

        // Set hint text color
        morningUpdateEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));
        eveningUpdateEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));

        // Initialize and set the adapter for the campusSpinner
        ArrayAdapter<String> campusSpinnerAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, new ArrayList<String>() {{
            addAll(Arrays.asList(getResources().getStringArray(R.array.campuses)));
        }}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));
                return view;
            }
        };
        campusSpinner.setAdapter(campusSpinnerAdapter);

        // Initialize and set the adapter for the themeSpinner
        ArrayAdapter<String> themeSpinnerAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, new ArrayList<String>() {{
            addAll(Arrays.asList(getResources().getStringArray(R.array.themes)));
        }}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Yellow));
                return view;
            }
        };
        themeSpinner.setAdapter(themeSpinnerAdapter);

        campusSpinner.setPopupBackgroundResource(R.color.Blue);
        themeSpinner.setPopupBackgroundResource(R.color.Blue);

        // Set click listener for the save settings button
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        // Set click listener for the back button
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    private void saveSettings() {
        // Get entered values
        String morningUpdateTime = morningUpdateEditText.getText().toString();
        String eveningUpdateTime = eveningUpdateEditText.getText().toString();
        String selectedCampus = campusSpinner.getSelectedItem().toString();
        String campusUrl = getCityUrlByName(selectedCampus);
        String selectedTheme = themeSpinner.getSelectedItem().toString();


        // Save settings to SharedPreferences
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).edit();
        editor.putString(MORNING_UPDATE_TIME_KEY, morningUpdateTime);
        editor.putString(EVENING_UPDATE_TIME_KEY, eveningUpdateTime);
        editor.putString(CAMPUS_KEY, campusUrl);
        editor.putString(THEME_KEY, selectedTheme);
        editor.apply();

        // Log SharedPreferences
        String logMessage = "Morning Update Time: " + morningUpdateTime +
                ", Evening Update Time: " + eveningUpdateTime +
                ", Campus: " + selectedCampus +
                ", Theme: " + selectedTheme;
        Log.d("SettingsFragment", logMessage);

        // Show success message
        Toast.makeText(getActivity(), "Settings saved", Toast.LENGTH_SHORT).show();
        WeatherUpdateScheduler.scheduleWeatherUpdates(getActivity());


    }
//
//    private void saveSettings() {
//        // Get entered values
//        String morningUpdateTime = morningUpdateEditText.getText().toString();
//        String eveningUpdateTime = eveningUpdateEditText.getText().toString();
//        String selectedCampus = campusSpinner.getSelectedItem().toString();
//        String campusUrl = getCityUrlByName(selectedCampus);
//        String selectedTheme = themeSpinner.getSelectedItem().toString();
//
//        // Check if morningUpdateTime and eveningUpdateTime are not empty
//        if (!morningUpdateTime.isEmpty() && !eveningUpdateTime.isEmpty()) {
//            // Save settings to SharedPreferences
//            SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).edit();
//            editor.putString(MORNING_UPDATE_TIME_KEY, morningUpdateTime);
//            editor.putString(EVENING_UPDATE_TIME_KEY, eveningUpdateTime);
//            editor.putString(CAMPUS_KEY, campusUrl);
//            editor.putString(THEME_KEY, selectedTheme);
//            editor.apply();
//
//            // Log SharedPreferences
//            String logMessage = "Morning Update Time: " + morningUpdateTime +
//                    ", Evening Update Time: " + eveningUpdateTime +
//                    ", Campus: " + selectedCampus +
//                    ", Theme: " + selectedTheme;
//            Log.d("SettingsFragment", logMessage);
//
//            // Show success message
//            Toast.makeText(getActivity(), "Settings saved", Toast.LENGTH_SHORT).show();
//            WeatherUpdateScheduler.scheduleWeatherUpdates(getActivity());
//        } else {
//            // Show an error message if either morningUpdateTime or eveningUpdateTime is empty
//            Toast.makeText(getActivity(), "Please enter both morning and evening update times", Toast.LENGTH_SHORT).show();
//        }
//    }



}
