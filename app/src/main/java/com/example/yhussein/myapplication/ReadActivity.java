package com.example.yhussein.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.*;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private TextView tvtitle,tvdescription,tvcategory;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);

        // Recieve data
        Intent intent = getIntent();
        String Id = intent.getExtras().getString("Id");
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        //get book content
        List<String> paragraphs = new ArrayList<>();
        paragraphs = getContent(this.getApplicationContext(), "book" + Id + ".txt");
        if(paragraphs.size() > 0) {
            tvdescription.setText(paragraphs.get(2));
        }

        // Setting values
        tvtitle.setText(Title);
        img.setImageResource(image);
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
