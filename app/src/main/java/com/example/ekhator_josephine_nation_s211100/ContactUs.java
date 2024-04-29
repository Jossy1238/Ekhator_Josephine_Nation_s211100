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

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class ContactUs extends Fragment {


    public ContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);


        // Set click listener for the back button
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        // Setup Spinner
        Spinner spinnerHelp = rootView.findViewById(R.id.spinnerHelp);

//        // Inside your onCreateView method, after setting up the Spinner
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.help_options, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerHelp.setAdapter(adapter);



// Corrected naming and method usage
        ArrayAdapter<String> spinnerHelpAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, new ArrayList<String>() {{
            addAll(Arrays.asList(getResources().getStringArray(R.array.help_options)));
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

        // Assuming you have a Spinner view with the id 'spinnerHelp' in your layout
        spinnerHelp.setAdapter(spinnerHelpAdapter); // Correctly setting the adapter on the Spinner

        spinnerHelp.setPopupBackgroundResource(R.color.Blue);


        // Setup RadioGroup
        RadioGroup radioGroupContact = rootView.findViewById(R.id.radioGroupContact);

        // Inside your onCreateView method, after setting up the RadioGroup
        RadioButton radioYes = rootView.findViewById(R.id.radioYes);
        RadioButton radioNo = rootView.findViewById(R.id.radioNo);

// Set the button tint to yellow
        radioYes.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.Yellow)));
        radioNo.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.Yellow)));


        // Setup EditTexts
        EditText editTextName = rootView.findViewById(R.id.editTextName);
        EditText editTextEmail = rootView.findViewById(R.id.editTextEmail);
        EditText editTextProblem = rootView.findViewById(R.id.editTextProblem);

        // Setup Submit Button
        Button submitButton = rootView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process form data here
                String selectedHelpOption = spinnerHelp.getSelectedItem().toString();
                String contactPreference = radioGroupContact.getCheckedRadioButtonId() == R.id.radioYes ? "Yes" : "No";
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String problem = editTextProblem.getText().toString();

                // Example: Display a toast message for demonstration
                Toast.makeText(getContext(), "Thank you for submitting, we will get back to you in 3 working days!", Toast.LENGTH_LONG).show();

                // Here you can add your logic to handle the form submission, e.g., send an email, save to a database, etc.
                // Clear the form
                editTextName.setText("");
                editTextEmail.setText("");
                editTextProblem.setText("");
                spinnerHelp.setSelection(0); // Reset the Spinner to the first item
                radioGroupContact.clearCheck(); // Clear the RadioGroup selection

            }
        });

        return rootView;
    }
}