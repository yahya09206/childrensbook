package com.example.yhussein.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class ReadActivity extends AppCompatActivity {

    private TextView tvtitle,tvdescription,tvcategory;
    private ImageView img;
    private String id;
    private int bookmark;
    private int section;
    private String language;
    private String sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);

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

        // Receive data
        Intent intent = getIntent();
        id = intent.getExtras().getString("Id");
        String Title = intent.getExtras().getString("Title");
        String Author = intent.getExtras().getString("Author");
        bookmark = intent.getExtras().getInt("Bookmark");
        language = intent.getExtras().getString("Language");
        sound = intent.getExtras().getString("Sound");
        section = bookmark + 1;

        String intro = "***** START READ OR LISTEN ******";

        try {
            //get book content
            List<String> paragraphs = new ArrayList<>();
            paragraphs = getContent(this.getApplicationContext(), "book" + id + "_" + language + ".txt");
            if (paragraphs.size() > 0) {
                tvdescription.setText(intro);
                tvcategory.setText("[" + bookmark + "/" + paragraphs.size() + "]");
            }

            // Setting values
            tvtitle.setText(Title + " by " + Author);

            String soundF = "audio" + id + "_" + section + "_" + language;
            Resources res = this.getApplicationContext().getResources();
            final int soundId = res.getIdentifier(soundF, "raw", getApplicationContext().getPackageName());
            final ArrayList<String> st = new ArrayList<>(paragraphs);
            final Button next = findViewById(R.id.next_button);
            final Button prev = findViewById(R.id.previous_button);
            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    bookmark++;
                    if (bookmark > st.size() - 1) {
                        bookmark = st.size() - 1;
                    }
                    section++;
                    if (section > st.size() - 1) {
                        section = st.size() - 1;
                    }
                    tvdescription.setText(st.get(bookmark));
                    tvcategory.setText("[" + bookmark + "/" + st.size() + "]");
                    final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundId);
                    if(sound.equals("On")) {

                        mp.start();
                        next.setEnabled(false);
                        next.setTextColor(getResources().getColor(R.color.colorAccent));
                        prev.setEnabled(false);
                        prev.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            finish(); // finish current activity
                            Toast.makeText(getApplicationContext(), "End of sound!", Toast.LENGTH_SHORT).show();
                            next.setEnabled(true);
                            next.setTextColor(getResources().getColor(R.color.colorPrimary));
                            prev.setEnabled(true);
                            prev.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    });
                }
            });

            prev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    bookmark--;
                    if (bookmark < 0) {
                        bookmark = 0;
                    }
                    section--;
                    if (section < 0) {
                        section = 0;
                    }
                    tvdescription.setText("[" + bookmark + "/" + st.size() + "] " + st.get(bookmark));
                    tvcategory.setText("[" + bookmark + "/" + st.size() + "]");
                    final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundId);
                    if(sound.equals("On")) {
                        mp.start();
                        prev.setEnabled(false);
                        prev.setTextColor(getResources().getColor(R.color.colorAccent));
                        next.setEnabled(false);
                        next.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            finish(); // finish current activity
                            Toast.makeText(getApplicationContext(), "End of sound!", Toast.LENGTH_SHORT).show();
                            next.setEnabled(true);
                            next.setTextColor(getResources().getColor(R.color.colorPrimary));
                            prev.setEnabled(true);
                            prev.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    });
                }
            });
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "State is corrupted, please reset!", Toast.LENGTH_SHORT).show();
        }

        final Button sett = findViewById(R.id.profile_button);
        sett.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
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
}
