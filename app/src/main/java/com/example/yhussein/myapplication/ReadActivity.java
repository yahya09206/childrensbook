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
import android.widget.ToggleButton;

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
    private String id2;
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
        id2 = id;
        sound = intent.getExtras().getString("Sound");
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
            final Spinner son = findViewById(R.id.son);

            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundId);

            lang.setSelection(((ArrayAdapter)lang.getAdapter()).getPosition(language));
            son.setSelection(((ArrayAdapter)lang.getAdapter()).getPosition(sound));
            if (paragraphs.size() > 0) {
                // Setting values
                img.setImageResource(pixId);
                tvdescription.setText("[" + bookmark + "/" + paragraphs.size() + "] " + paragraphs.get(bookmark));
            }

            play.setVisibility(View.VISIBLE);
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
                        Toast.makeText(ReadActivity.this, "language : " + item.toString(),
                                Toast.LENGTH_SHORT).show();
                        language = item.toString();

                        List<State> lg = db.statesDao().getAllStates();
                        if(!language.equals(lg.get(0).getReaderLanguage())) {
                            Toast.makeText(ReadActivity.this, "switching to : " + item.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                            intent.putExtra("Id", id2);
                            intent.putExtra("Sound", sound);
                            intent.putExtra("Section", section);
                            intent.putExtra("Language", language);
                            intent.putExtra("Bookmark", bookmark);
                            getApplication().startActivity(intent);
                        }
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
                        Toast.makeText(ReadActivity.this, "sound is : " + item.toString(),
                                Toast.LENGTH_SHORT).show();
                        sound = item.toString();

                        List<State> lg = db.statesDao().getAllStates();
                        if(!sound.equals(lg.get(0).getSoundStatus())) {
                            Toast.makeText(ReadActivity.this, "turning sound : " + item.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                            intent.putExtra("Id", id2);
                            intent.putExtra("Sound", sound);
                            intent.putExtra("Section", section);
                            intent.putExtra("Language", language);
                            intent.putExtra("Bookmark", bookmark);
                            getApplication().startActivity(intent);
                        }
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
                        play.setVisibility(View.VISIBLE);
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
                    // start the activity
                    getApplication().startActivity(intent);
                }
            });

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "oops!!!", Toast.LENGTH_SHORT).show();
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

    /*public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    //...

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            //Spinner mySpinner = (Spinner) findViewById(R.id.lang);
            language = parent.getItemAtPosition(pos).toString();
            sound = parent.getItemAtPosition(pos).toString();

            Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
            // passing data to the book activity
            intent.putExtra("Id", id);
            intent.putExtra("Section", section);
            intent.putExtra("Language", language);
            intent.putExtra("Bookmark", bookmark);
            // start the activity
            getApplication().startActivity(intent);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
