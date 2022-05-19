package com.example.servicefactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FirebaseAuth mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewList = findViewById(R.id.view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        ArrayList<ListDomain> articles = new ArrayList<>();
        articles.add(new ListDomain("Caitlyn Jenner in \"Hell no\"", "pic1"));
        articles.add(new ListDomain("Gov. Brian kemp", "pic2"));
        articles.add(new ListDomain("US-China War", "pic3"));
        articles.add(new ListDomain("Caitlyn Jenner in \"Hell no\"", "pic1"));

        adapter = new DashboardAdapter(articles);
        recyclerViewList.setAdapter(adapter);
        mFirebase=FirebaseAuth.getInstance();

    }


}