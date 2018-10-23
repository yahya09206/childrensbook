package com.example.yhussein.myapplication;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    List<Setting> localRecords;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this.getApplicationContext());
        localRecords = db.settingsDao().getAllBooks();

        if(localRecords.size() == 0) {
            localRecords.add(new Setting(R.drawable.image1, "Great Book Title 1", "Mekone Tolrom", "Mekone", "0", "English", "Off", "1", "audio1.txt", "content1.txt", 0));
            localRecords.add(new Setting(R.drawable.image2, "Great Book Title 2", "Yahya Hussein", "Yahya", "0", "French", "Off", "2", "audio2.txt", "content2.txt", 0));
            localRecords.add(new Setting(R.drawable.image3, "Great Book Title 3", "Gerald Gate", "Gerald", "0", "Arabic", "Off", "3", "audio3.txt", "content3.txt", 0));
            localRecords.add(new Setting(R.drawable.image4, "Great Book Title 4", "Steve Paul", "Steve", "0", "Spanish", "Off", "4", "audio4.txt", "content4.txt", 0));
            localRecords.add(new Setting(R.drawable.image5, "Great Book Title 5", "Kyle Smith", "Kyle", "0", "Chinese", "Off", "5", "audio5.txt", "content5.txt", 0));

            Toast.makeText(this, localRecords.size() + " books locally loaded!!", Toast.LENGTH_LONG).show();

            int count = 0;
            for(int i = 0; i < localRecords.size(); i++) {
                insertDatabase(localRecords.get(i));
                //db.settingsDao().delete(localRecords.get(i));
                count++;
            }
            Toast.makeText(this, count + " books inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Welcome back! You have " + localRecords.size() + " books!", Toast.LENGTH_LONG).show();
        }

        RecyclerView myRecyclerview = (RecyclerView) findViewById(R.id.recyclerView_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,localRecords);
        myRecyclerview.setLayoutManager(new GridLayoutManager(this,1));
        myRecyclerview.setAdapter(myAdapter);
    }

    @Override
    protected void onDestroy(){
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    //update
    public void updateDatabase(View view, Setting setting) {
        new UpdateSettingTask(this, setting).execute();
    }

    private static class UpdateSettingTask extends AsyncTask<Void, Void, Setting> {

        private WeakReference<Activity> weakActivity;
        private Setting setting;

        public UpdateSettingTask(Activity activity, Setting setting) {
            weakActivity = new WeakReference<>(activity);
            this.setting = setting;
        }

        @Override
        protected Setting doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            db.settingsDao().updateSettings(setting);
            return setting;
        }
    }

    //insert
    public void insertDatabase(Setting setting) {
        new InsertSettingTask(this, setting).execute();
    }

    private static class InsertSettingTask extends AsyncTask<Void, Void, Setting> {

        private WeakReference<Activity> weakActivity;
        private Setting setting;

        public InsertSettingTask(Activity activity, Setting setting) {
            weakActivity = new WeakReference<>(activity);
            this.setting = setting;
        }

        @Override
        protected Setting doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            db.settingsDao().insertAll(setting);
            return setting;
        }
    }

    //get
    private static List<Setting> loadAllBooks(final AppDatabase db) {
        List<Setting> settings = db.settingsDao().getAllBooks();
        return settings;
    }

    private static class GetSettingTask extends AsyncTask<Void, Void, Setting> {

        private WeakReference<Activity> weakActivity;

        public GetSettingTask(Activity activity) {
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Setting doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            List<Setting> users = db.settingsDao().getAllBooks();
            if (users.size() <= 0 || users.get(0) == null) {
                return null;
            }
            return users.get(0);
        }
        @Override
        protected void onPostExecute(Setting setting) {
            MainActivity activity = (MainActivity) weakActivity.get();
            if(setting == null || activity == null) {
                return;
            }

            //edit_email.setText(setting.getEmail());
            //edit_dailyReminder.setText(setting.getDailyReminder());
            //edit_minDistance.setText(setting.getMinDistance());
            //edit_maxDistance.setText(setting.getMaxDistance());
            //edit_maleFemale.setText(setting.getMaleFemale());
            //edit_accountStatus.setText(setting.getAccountStatus());
            //edit_minAge.setText(setting.getMinAge());
            //edit_maxAge.setText(setting.getMaxAge());
            //edit_photoUrl.setText(setting.getPhotoUrl());
        }
    }
}
