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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private List<CityWeather> cityWeathers; // Updated to use CityWeather objects

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cityWeathers = new ArrayList<>();
        cityAdapter = new CityAdapter(cityWeathers, (position, cityWeather) -> navigateToHomeFragment(cityWeather.getUrl()));
        recyclerView.setAdapter(cityAdapter);

        String[] urls = {
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241"
        };

        ExecutorService executorService = Executors.newFixedThreadPool(urls.length);
        for (String url : urls) {
            fetchCityData(executorService, url);
        }

        return view;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void fetchCityData(ExecutorService executorService, String urlLink) {
        executorService.submit(() -> {
            if (!isNetworkAvailable()) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Please connect to a network", Toast.LENGTH_LONG).show();
                });
                return;
            }

            CurrentWeather currentWeather = new CurrentWeather();
            List<CurrentWeather> weathers = currentWeather.parseXMLFromFuture(getContext(), urlLink);

            if (weathers != null && !weathers.isEmpty()) {
                getActivity().runOnUiThread(() -> {
                    cityWeathers.add(new CityWeather(weathers.get(0), urlLink));
                    cityAdapter.notifyDataSetChanged();
                });
            } else {
                // Handle case where weather data is not available
            }
        });
    }



    private void navigateToHomeFragment(String url) {
        // Create a new instance of the Home fragment and set the URL as an argument
        TheHomeFragment homeFragment = new TheHomeFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        homeFragment.setArguments(args);

        // Navigate to the Home fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
