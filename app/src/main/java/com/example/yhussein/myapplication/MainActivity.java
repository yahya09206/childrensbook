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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    List<Setting> localRecords;
    List<State> sRecords;
    private AppDatabase db;
    private int resetCount = 0; //1 = delete all, 0 = keep old data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this.getApplicationContext());

        localRecords = db.settingsDao().getAllBooks();
        sRecords = db.statesDao().getAllStates();
        if(localRecords.size() == 0 && resetCount == 0) {
            localRecords.add(new Setting(R.drawable.image1_1, "Great Book Title 1", "Mekone Tolrom", "Mekone", "0", "0", "On", "1", "audio1_english.mp3", "book1_english.txt", 0));
            localRecords.add(new Setting(R.drawable.image1_2, "Great Book Title 2", "Yahya Smith", "Yahya", "0", "0", "On", "2", "audio2_english.mp3", "book2_english.txt", 0));
            //book 3
            //book 4
            //book 5

            Toast.makeText(this, localRecords.size() + " books locally loaded!!", Toast.LENGTH_LONG).show();

            int count = 0;
            for(int i = 0; i < localRecords.size(); i++) {
                insertDatabase(localRecords.get(i));
                count++;
            }
            Toast.makeText(this, count + " books inserted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Welcome back! You have " + localRecords.size() + " books!", Toast.LENGTH_LONG).show();
        }
        //create state
        if(sRecords.size() == 0) {
            State s = new State();
            s.setStateId(1);
            s.setBookMark(0);
            s.setReaderIp("0.0.0.0");
            s.setReaderLanguage("english");
            s.setSoundStatus("On");
            insertDatabase(s);
        }

        //reset state, delete state
        if(resetCount == 1 && sRecords.size() > 0) {
            db.statesDao().delete(sRecords.get(0));
        }
        //reset records, delete all records
        if(resetCount == 1 && localRecords.size() > 0) {
            for (int i = 0; i < localRecords.size(); i++) {
                db.settingsDao().delete(localRecords.get(i));
            }
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
        }
    }

    /****************STATE**********************/
    //update
    public void updateDatabase(View view, State state) {
        new UpdateStateTask(this, state).execute();
    }

    private static class UpdateStateTask extends AsyncTask<Void, Void, State> {

        private WeakReference<Activity> weakActivity;
        private State state;

        public UpdateStateTask(Activity activity, State state) {
            weakActivity = new WeakReference<>(activity);
            this.state = state;
        }

        @Override
        protected State doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            db.statesDao().updateStates(state);
            return state;
        }
    }

    //insert
    public void insertDatabase(State state) {
        new InsertStateTask(this, state).execute();
    }

    private static class InsertStateTask extends AsyncTask<Void, Void, State> {

        private WeakReference<Activity> weakActivity;
        private State state;

        public InsertStateTask(Activity activity, State state) {
            weakActivity = new WeakReference<>(activity);
            this.state = state;
        }

        @Override
        protected State doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            db.statesDao().insertAll(state);
            return state;
        }
    }

    //get
    private static List<State> loadAllStates(final AppDatabase db) {
        List<State> states = db.statesDao().getAllStates();
        return states;
    }

    private static class GetStateTask extends AsyncTask<Void, Void, State> {

        private WeakReference<Activity> weakActivity;

        public GetStateTask(Activity activity) {
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected State doInBackground(Void... voids) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getAppDatabase(activity.getApplicationContext());
            List<State> states = db.statesDao().getAllStates();
            if (states.size() <= 0 || states.get(0) == null) {
                return null;
            }
            return states.get(0);
        }
        @Override
        protected void onPostExecute(State state) {
            MainActivity activity = (MainActivity) weakActivity.get();
            if(state == null || activity == null) {
                return;
            }
        }
    }
}
