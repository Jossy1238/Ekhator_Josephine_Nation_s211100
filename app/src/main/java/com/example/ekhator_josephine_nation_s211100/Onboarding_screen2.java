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

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Onboarding_screen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen2);

        // Find the ImageView in the layout
        ImageView imageView = findViewById(R.id.imageView2);

        // Calculate the percentage of screen width for movement
        float movementPercentage = 0.2f; // Adjust as needed

        // Define animation for ImageView
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -movementPercentage,
                Animation.RELATIVE_TO_SELF, movementPercentage,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(1000); // Set duration for animation (in milliseconds)
        animation.setRepeatCount(Animation.INFINITE); // Make the animation repeat indefinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse the animation when it reaches the end

        // Start animation on the ImageView
        imageView.startAnimation(animation);

        // Find the "Next" button and set its click listener
        Button btnGetStarted = findViewById(R.id.button);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Onboarding_screen2.this, Onboarding_screen3.class));
                finish();
            }
        });

        // Find the "Skip" TextView and set its click listener
        TextView textViewSkip = findViewById(R.id.textViewSkip);
        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Onboarding_screen2.this, MainActivity.class));
                finish();
            }
        });
    }
}