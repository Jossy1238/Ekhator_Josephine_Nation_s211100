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

import static android.content.ContentValues.TAG;
import static com.example.ekhator_josephine_nation_s211100.SettingsFragment.EVENING_UPDATE_TIME_KEY;
import static com.example.ekhator_josephine_nation_s211100.SettingsFragment.MORNING_UPDATE_TIME_KEY;
import static com.example.ekhator_josephine_nation_s211100.SettingsFragment.PREF_FILE_NAME;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class WeatherUpdateScheduler {

    private static final String DEFAULT_MORNING_UPDATE_TIME = "08:00";
    private static final String DEFAULT_EVENING_UPDATE_TIME = "20:00";

    public static void scheduleWeatherUpdates(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        String morningUpdateTime = sharedPreferences.getString(MORNING_UPDATE_TIME_KEY, DEFAULT_MORNING_UPDATE_TIME);
        String eveningUpdateTime = sharedPreferences.getString(EVENING_UPDATE_TIME_KEY, DEFAULT_EVENING_UPDATE_TIME);

        // Schedule morning update
        scheduleAlarm(context, morningUpdateTime, AlarmType.MORNING_UPDATE);

        // Schedule evening update
        scheduleAlarm(context, eveningUpdateTime, AlarmType.EVENING_UPDATE);
    }

    @SuppressLint("ScheduleExactAlarm")
    private static void scheduleAlarm(Context context, String time, AlarmType alarmType) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WeatherUpdateReceiver.class);
        intent.putExtra("ALARM_TYPE", alarmType.toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Calendar alarmTime = getTimeFromTimeString(time);

        Log.d(TAG, "Scheduling alarm for " + time);

        // Use setExact for more precise timing
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
    }

    private static Calendar getTimeFromTimeString(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return calendar;
    }

    public enum AlarmType {
        MORNING_UPDATE,
        EVENING_UPDATE
    }
}


