package com.example.yhussein.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstBook = new ArrayList<>();
        lstBook.add(new Book("The Vegetarian","Categorie Book","Description book",R.drawable.thevegetarian));
        lstBook.add(new Book("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        lstBook.add(new Book("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        lstBook.add(new Book("The Martian","Categorie Book","Description book",R.drawable.themartian));
        lstBook.add(new Book("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        lstBook.add(new Book("Privacy","Categorie Book","Description book",R.drawable.privacy));
        lstBook.add(new Book("The Best Laid Plans","Categorie Book","Description book",R.drawable.bestlaid));
        lstBook.add(new Book("Quesadillas","Categorie Book","Description book",R.drawable.quesadillas));
        lstBook.add(new Book("Origin","Categorie Book","Description book",R.drawable.origin));

        RecyclerView myRecyclerview = (RecyclerView) findViewById(R.id.recyclerView_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        myRecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        myRecyclerview.setAdapter(myAdapter);
    }
}
