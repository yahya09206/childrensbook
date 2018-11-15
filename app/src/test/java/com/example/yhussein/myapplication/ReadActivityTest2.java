package com.example.yhussein.myapplication;

import android.app.Application;
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReadActivityTest2 extends Application {

    private static Context context;
    private static State state;
    private String filePath = "book1_english.txt";

    public static Context getContext(){
        return  context;
    }

    @Before
    public void setUp() throws Exception {
        state = new State();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
        state.setReaderLanguage("french");
        assertEquals(state.getReaderLanguage(), "french");
    }

    @Test
    public void onTouchEvent() {
    }

    @Test
    public void getContent() {
        List<String> list = new ArrayList<>();
        //list = ReadActivity.getContent(ReadActivityTest2.getContext(), filePath);
        //assertEquals(list.size(), 1);
    }

    @Test
    public void onItemClick() {
    }

    @Test
    public void updateDatabase() {
        state.setReaderLanguage("english");
        //ReadActivity.updateDatabase(ReadActivity.class, state);
        assertEquals(state.getReaderLanguage(), "english");
    }
}