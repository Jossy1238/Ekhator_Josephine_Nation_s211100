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


import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mMapView;
    private List<MapWeatherData> mapWeatherDataList;
    private List<Marker> markers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Get the MapView from the layout
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        // Fetch weather data and convert to map data objects
        List<String> urls = getWeatherURLs();
        mapWeatherDataList = FetchWeatherTask.convertToMapWeatherData(urls);

        // Initialize the search functionality
        initializeSearch(rootView);

        return rootView;
    }

    private void initializeSearch(View rootView) {
        SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This method is called when the text in the SearchView changes
                // You can perform any filtering or suggestions related operations here
                return false;
            }
        });

        // Submit the query automatically when the user presses Enter
        searchView.setSubmitButtonEnabled(true);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }



    private void handleSearch(String cityName) {
        Geocoder geocoder = new Geocoder(requireContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng location = new LatLng(address.getLatitude(), address.getLongitude());

                // Fetch weather data for the searched location
                String cityUrl = FetchWeatherTask.getCityUrlByName(cityName);
                if (cityUrl != null) {
                    List<CurrentWeather> weatherData = FetchWeatherTask.fetchWeatherData(getContext(), cityUrl);
                    if (!weatherData.isEmpty()) {
                        MapWeatherData mapWeatherData = FetchWeatherTask.getMapWeatherData(weatherData.get(0));

                        // Clear previous markers
                        mMap.clear();

                        // Add a marker for the searched location with weather information
                        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(cityName));
                        marker.setTag(mapWeatherData); // Set the tag to hold the map data object
                        markers.add(marker); // Add the marker to your list of markers

                        // Move the camera to the searched location
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                    } else {
                        Toast.makeText(getContext(), "No weather data found for " + cityName, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid city name: " + cityName, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for each location
        for (MapWeatherData mapWeatherData : mapWeatherDataList) {
            LatLng location = new LatLng(Double.parseDouble(mapWeatherData.getLatitude()),
                    Double.parseDouble(mapWeatherData.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(mapWeatherData.getLocation()));
            marker.setTag(mapWeatherData); // Set the tag to hold the map data object
            markers.add(marker); // Add the marker to your list of markers
        }

        // Set map type (normal, satellite, hybrid)
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Move camera to the first marker
        if (!mapWeatherDataList.isEmpty()) {
            LatLng firstLocation = new LatLng(Double.parseDouble(mapWeatherDataList.get(0).getLatitude()),
                    Double.parseDouble(mapWeatherDataList.get(0).getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10));
        }

        // Set marker click listener
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Retrieve the map data object from the marker's tag
                MapWeatherData mapWeatherData = (MapWeatherData) marker.getTag();
                if (mapWeatherData != null) {
                    // Display details of the object associated with the marker
                    displayWeatherDetails(mapWeatherData);
                }
                return true;
            }
        });
    }

    // Method to display weather details in a dialog
    private void displayWeatherDetails(MapWeatherData mapWeatherData) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_weather_details);

        TextView locationTextView = dialog.findViewById(R.id.locationTextView);
        TextView minTempTextView = dialog.findViewById(R.id.minTempTextView);
        TextView maxTempTextView = dialog.findViewById(R.id.maxTempTextView);
        TextView pressureTextView = dialog.findViewById(R.id.pressureTextView);
        TextView humidityTextView = dialog.findViewById(R.id.humidityTextView);

        locationTextView.setText("Coordinates: " + mapWeatherData.getLocation());
        minTempTextView.setText("Min Temp: " + mapWeatherData.getMinimumTemperature());
        maxTempTextView.setText("Max Temp: " + mapWeatherData.getMaximumTemperature());
        pressureTextView.setText("Pressure: " + mapWeatherData.getPressure());
        humidityTextView.setText("Humidity: " + mapWeatherData.getHumidity());

        dialog.show();
    }

    // Method to get the list of weather URLs
    private List<String> getWeatherURLs() {
        List<String> urls = new ArrayList<>();
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286");
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
        urls.add("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241");
        return urls;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
