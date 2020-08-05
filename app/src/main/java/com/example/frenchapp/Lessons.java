package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lessons extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView backbtn;
    private CustomAdapter adapter;
    private LinearLayout quiz1,quiz2;
    private TextView courseTitle;

    private ListView listview;

    private ArrayList<LessonsList> lessonsLists = new ArrayList<LessonsList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Intent in = getIntent();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listview = findViewById(R.id.list_view);
        backbtn = findViewById(R.id.backbtn);

        courseTitle = findViewById(R.id.courseTitle);
        courseTitle.setText(in.getStringExtra("title"));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        quiz1 = findViewById(R.id.quiz1);
        quiz2 = findViewById(R.id.quiz2);

        quiz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Lessons.this,Quiz.class);
                in.putExtra("quiz_no","quiz1");
                startActivity(in);
            }
        });

        quiz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Lessons.this,Quiz.class);
                in.putExtra("quiz_no","quiz2");
                startActivity(in);
            }
        });

        mDatabase.child("BasicCourse").child("Lessons").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    lessonsLists.add((LessonsList) dataSnapshot.getValue(LessonsList.class));

                }

                adapter = new CustomAdapter(lessonsLists,Lessons.this);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new FinestWebView.Builder(Lessons.this).show(lessonsLists.get(position).getLessonUrl());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
