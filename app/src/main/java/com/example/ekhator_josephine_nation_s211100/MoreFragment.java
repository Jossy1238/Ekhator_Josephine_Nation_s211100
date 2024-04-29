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

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MoreFragment extends Fragment {

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        // Set click listeners for each layout to navigate to respective fragments
        LinearLayout settingsLayout = view.findViewById(R.id.settingsFragmentRoot);
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSettingsFragment();
            }
        });

//        // Set click listener for the Compare Cities layout
//        LinearLayout compareCitiesLayout = view.findViewById(R.id.compareCitiesFragmentRoot);
//        compareCitiesLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToCompareCitiesFragment();
//            }
//        });

        // Set click listener for the About This App layout
        LinearLayout aboutThisAppLayout = view.findViewById(R.id.aboutThisAppRoot);
        aboutThisAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAboutThisAppFragment();
            }
        });

        // Set click listener for the Contact Us layout
        LinearLayout contactUsLayout = view.findViewById(R.id.contactUsRoot);
        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToContactUsFragment();
            }
        });

        // Set click listener for the Help Fragment layout
        LinearLayout helpFragmentLayout = view.findViewById(R.id.helpFragmentRoot);
        helpFragmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHelpFragment();
            }
        });

        return view;
    }

    private void navigateToSettingsFragment() {
        // Navigate to the SettingsFragment
        Fragment settingsFragment = new SettingsFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

//    private void navigateToCompareCitiesFragment() {
//        // Navigate to the CompareTwoCitiesInfo Fragment
//        Fragment compareCitiesFragment = new CompareTwoCitiesInfo();
//        getParentFragmentManager().beginTransaction()
//                .replace(R.id.container, compareCitiesFragment)
//                .addToBackStack(null)
//                .commit();
//    }

    private void navigateToAboutThisAppFragment() {
        // Navigate to the AboutThisApp Fragment
        Fragment aboutThisAppFragment = new AboutThisApp();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, aboutThisAppFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToContactUsFragment() {
        // Navigate to the ContactUs Fragment
        Fragment contactUsFragment = new ContactUs();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, contactUsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToHelpFragment() {
        // Navigate to the HelpFragment
        Fragment helpFragment = new HelpFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, helpFragment)
                .addToBackStack(null)
                .commit();
    }
}

