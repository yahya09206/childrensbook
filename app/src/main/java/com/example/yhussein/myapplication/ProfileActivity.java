package com.example.yhussein.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button save = findViewById(R.id.save_button);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button home = findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /****************STATE**********************/
    //update
    public void updateDatabase(View view, State state) {
        new ProfileActivity.UpdateStateTask(this, state).execute();
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
        new ProfileActivity.InsertStateTask(this, state).execute();
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
