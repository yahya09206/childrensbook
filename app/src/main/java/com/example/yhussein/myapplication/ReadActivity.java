package com.example.yhussein.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
    private int bookmark;
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
        String Id = intent.getExtras().getString("Id");
        String Title = intent.getExtras().getString("Title");
        String Author = intent.getExtras().getString("Author");
        bookmark = intent.getExtras().getInt("Bookmark");
        language = intent.getExtras().getString("Language");
        sound = intent.getExtras().getString("Sound");

        try {
            //get book content
            List<String> paragraphs = new ArrayList<>();
            paragraphs = getContent(this.getApplicationContext(), "book" + Id + "_" + language + ".txt");
            if (paragraphs.size() > 0) {
                tvdescription.setText(paragraphs.get(bookmark));
                tvcategory.setText("[" + bookmark + "/" + paragraphs.size() + "]");
                if(sound.equals("On")) {
                    if (language.equals("english")) {
                        if (Id.equals("1")) {
                            final MediaPlayer mp = MediaPlayer.create(this, R.raw.audio1_english);
                            mp.start();
                        }
                    }
                }
            }

            // Setting values
            tvtitle.setText(Title + " by " + Author);

            final ArrayList<String> st = new ArrayList<>(paragraphs);

            final Button next = findViewById(R.id.next_button);
            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    bookmark++;
                    if (bookmark > st.size() - 1) {
                        bookmark = st.size() - 1;
                    }
                    tvdescription.setText(st.get(bookmark));
                    tvcategory.setText("[" + bookmark + "/" + st.size() + "]");
                }
            });

            final Button prev = findViewById(R.id.previous_button);
            prev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    bookmark--;
                    if (bookmark < 0) {
                        bookmark = 0;
                    }
                    tvdescription.setText("[" + bookmark + "/" + st.size() + "] " + st.get(bookmark));
                    tvcategory.setText("[" + bookmark + "/" + st.size() + "]");
                }
            });
        }catch (Exception ex){
            tvdescription.setText("State is corrupted, please reset!");
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
