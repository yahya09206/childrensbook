package com.example.yhussein.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    private TextView tvdescription;
    private ImageView img;
    private String id;
    private String id2;
    private int bookmark;
    private int section;
    private String language;
    private String sound;
    String soundF;
    String pix;
    boolean running = false;
    private AppDatabase db;
    private ScrollView sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        db = AppDatabase.getAppDatabase(getApplicationContext());

        img = (ImageView) findViewById(R.id.book_img_id);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvdescription.setMovementMethod(new ScrollingMovementMethod());

        final Button close = findViewById(R.id.close);

        // Receive data
        Intent intent = getIntent();
        id = intent.getExtras().getString("Id");
        id2 = id;
        sound = intent.getExtras().getString("Sound");
        bookmark = intent.getExtras().getInt("Bookmark");
        language = intent.getExtras().getString("Language");
        section = intent.getExtras().getInt("Section");
        if(section == 0) {
            section = bookmark + 1;
        }

        List<String> paragraphs = new ArrayList<>();
        try {
            //get book content
            paragraphs = getContent(this.getApplicationContext(), "book" + id + "_" + language + ".txt");

            soundF = "audio" + id + "_" + section + "_" + language;
            pix = "image" + id + "_" + section;
            Resources res = this.getApplicationContext().getResources();
            final int pixId = res.getIdentifier(pix, "drawable", getApplicationContext().getPackageName());
            final int soundId = res.getIdentifier(soundF, "raw", getApplicationContext().getPackageName());
            final ArrayList<String> st = new ArrayList<>(paragraphs);

            final ImageView imgButton = (ImageView) findViewById(R.id.book_img_id);

            final Button prev = findViewById(R.id.prev);
            final Button play = findViewById(R.id.play);
            final Button pause = findViewById(R.id.pause);
            final Button next = findViewById(R.id.next);
            final Spinner lang = findViewById(R.id.lang);
            final Spinner son = findViewById(R.id.son);

            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundId);

            if (st.size() > 0) {
                //set image and text values
                img.setImageResource(pixId);
                tvdescription.setText("[" + bookmark + "/" + st.size() + " - " + language + "] " + paragraphs.get(bookmark));
            }

            if(sound.equals("On")) {
                play.setVisibility(View.VISIBLE);
            }else{
                play.setVisibility(View.GONE);
            }
            prev.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
            lang.setVisibility(View.VISIBLE);
            son.setVisibility(View.VISIBLE);

            lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        String language2 = item.toString();

                        List<State> lg = db.statesDao().getAllStates();
                        //update language value
                        if (lg != null) {
                            if(language2.equals("Lang")){
                                return;
                            }
                            else{
                                if(mp.isPlaying()) {
                                    mp.stop();
                                }
                                language = language2;
                                lg.get(0).setReaderLanguage(language);
                                updateDatabase(lg.get(0));
                                Toast.makeText(ReadActivity.this, "switching to : " + language,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                        intent.putExtra("Id", id2);
                        intent.putExtra("Sound", sound);
                        intent.putExtra("Section", section);
                        intent.putExtra("Language", language);
                        intent.putExtra("Bookmark", bookmark);
                        getApplication().startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // TODO Auto-generated method stub
                }
            });

            son.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        String sound2 = item.toString();

                        List<State> sd = db.statesDao().getAllStates();
                        //update sound status
                        if (sd != null) {
                            if(sound2.equals("Sound")) {
                                return;
                            }else{
                                if(mp.isPlaying()) {
                                    mp.stop();
                                }
                                sound = sound2;
                                sd.get(0).setSoundStatus(sound);
                                updateDatabase(sd.get(0));
                                Toast.makeText(ReadActivity.this, "turning sound : " + sound,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                        intent.putExtra("Id", id2);
                        intent.putExtra("Sound", sound);
                        intent.putExtra("Section", section);
                        intent.putExtra("Language", language);
                        intent.putExtra("Bookmark", bookmark);
                        getApplication().startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // TODO Auto-generated method stub
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    mp.stop();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            imgButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(running){
                        pause.setVisibility(View.VISIBLE);
                    }
                    else {
                        if(sound.equals("On")) {
                            play.setVisibility(View.VISIBLE);
                        }else{
                            play.setVisibility(View.GONE);
                        }
                    }
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    lang.setVisibility(View.VISIBLE);
                    son.setVisibility(View.VISIBLE);
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    play.setVisibility(View.GONE);
                    prev.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    lang.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);
                    son.setVisibility(View.GONE);
                    tvdescription.setMovementMethod(new ScrollingMovementMethod());

                    if(sound.equals("On")) {
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                finish(); // finish current activity
                                Toast.makeText(getApplicationContext(), "End of sound!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    running = true;
                }
            });

            prev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    mp.stop();
                    bookmark--;
                    if (bookmark < 0) {
                        bookmark = 0;
                    }
                    section--;
                    if (section < 0) {
                        section = 0;
                    }

                    Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                    // passing data to the book activity
                    intent.putExtra("Id", id);
                    intent.putExtra("Section", section);
                    intent.putExtra("Language", language);
                    intent.putExtra("Bookmark", bookmark);
                    intent.putExtra("Sound", sound);
                    // start the activity
                    getApplication().startActivity(intent);
                }
            });

            pause.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    if(mp.isPlaying()) {
                        mp.pause();
                    }
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                    running = false;
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mp.stop();
                    bookmark++;
                    if (bookmark > st.size() - 1) {
                        bookmark = st.size() - 1;
                    }
                    section++;
                    if (section > st.size() - 1) {
                        section = st.size() - 1;
                    }

                    Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                    // passing data to the book activity
                    intent.putExtra("Id", id);
                    intent.putExtra("Section", section);
                    intent.putExtra("Language", language);
                    intent.putExtra("Bookmark", bookmark);
                    intent.putExtra("Sound", sound);
                    // start the activity
                    getApplication().startActivity(intent);
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "rec=" + paragraphs.size(), Toast.LENGTH_SHORT).show();
        }
    }

    // Catch touch events here
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //System.out.println("Touch Up X:" + event.getX() + " Y:" + event.getY());
        }
        return super.onTouchEvent(event);
    }

    public static List<String> getContent(Context context, String filePath)
    {
        List<String> paragraphs = new ArrayList<>();
        AssetManager mgr = context.getAssets();
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mgr.open(filePath)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                sb.append(mLine);
            }
            String[] splittedLine = sb.toString().split("\\[");
            paragraphs = new ArrayList<String>(Arrays.asList(splittedLine));
        } catch (IOException e) {
            //log the exception
            String sError = e.getMessage();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //get error message string
                    String msg = e.toString();
                }
            }
        }

        return paragraphs;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /****************STATE**********************/
    //update
    public void updateDatabase(State state) {
        new ReadActivity.UpdateStateTask(this, state).execute();
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
}
