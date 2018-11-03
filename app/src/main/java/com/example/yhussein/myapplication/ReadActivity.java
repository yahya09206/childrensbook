package com.example.yhussein.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    private TextView tvdescription;
    private ImageView img;
    private String id;
    private int bookmark;
    private int section;
    private String language;
    private String sound;
    String soundF;
    String pix;
    boolean running = false;
    private AppDatabase db;

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
        bookmark = intent.getExtras().getInt("Bookmark");
        language = intent.getExtras().getString("Language");
        section = intent.getExtras().getInt("Section");
        if(section == 0) {
            section = bookmark + 1;
        }

        try {
            //get book content
            List<String> paragraphs = new ArrayList<>();
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

            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundId);

            if (paragraphs.size() > 0) {
                // Setting values
                img.setImageResource(pixId);
                tvdescription.setText(paragraphs.get(bookmark));
            }

            play.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
            lang.setVisibility(View.VISIBLE);

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
                        play.setVisibility(View.VISIBLE);
                    }
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    lang.setVisibility(View.VISIBLE);
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
                    tvdescription.setMovementMethod(new ScrollingMovementMethod());

                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            finish(); // finish current activity
                            Toast.makeText(getApplicationContext(), "End of sound!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    running = true;
                }
            });

            prev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    mp.stop();
                    section--;
                    if (section < 0) {
                        section = 0;
                    }

                    Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                    // passing data to the book activity
                    intent.putExtra("Id", id);
                    intent.putExtra("Section", section);
                    intent.putExtra("Language", language);
                    // start the activity
                    getApplication().startActivity(intent);
                }
            });

            pause.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    mp.pause();
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                    running = false;
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mp.stop();
                    section++;
                    if (section > st.size() - 1) {
                        section = st.size() - 1;
                    }

                    Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                    // passing data to the book activity
                    intent.putExtra("Id", id);
                    intent.putExtra("Section", section);
                    intent.putExtra("Language", language);
                    // start the activity
                    getApplication().startActivity(intent);
                }
            });

            lang.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    //Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    //R.array.planets_array, android.R.layout.simple_spinner_item);
                    // Specify the layout to use when the list of choices appears
                    //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    //spinner.setAdapter(adapter);

                    //spinner = (Spinner) findViewById(R.id.planets_spinner);
                    //spinner.setOnItemSelectedListener(this.onContextItemSelected());
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "A little issue going on!", Toast.LENGTH_SHORT).show();
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

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    //...

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
