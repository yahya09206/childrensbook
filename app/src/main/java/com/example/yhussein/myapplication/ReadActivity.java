package com.example.yhussein.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);

        // Receieve data
        Intent intent = getIntent();
        String Id = intent.getExtras().getString("Id");
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");
        bookmark = 0;
        language = "English";

        //get book content
        List<String> paragraphs = new ArrayList<>();
        paragraphs = getContent(this.getApplicationContext(), "book" + Id + ".txt");
        if(paragraphs.size() > 0) {
            tvdescription.setText(paragraphs.get(bookmark));
        }

        // Setting values
        tvtitle.setText(Title);
        img.setImageResource(image);

        final ArrayList<String> st = new ArrayList<>(paragraphs);

        final Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                bookmark++;
                if(bookmark > st.size() - 1){
                    bookmark = st.size() - 1;
                }
                tvdescription.setText(st.get(bookmark));
            }
        });

        final Button prev = findViewById(R.id.previous_button);
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                bookmark--;
                if(bookmark < 0){
                    bookmark = 0;
                }
                tvdescription.setText(st.get(bookmark));
            }
        });

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
                    //log the exception
                }
            }
        }

        return paragraphs;
    }
}
