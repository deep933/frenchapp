package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ImageView profile;

    private LinearLayout basic_course,inter_course,advance_course;
    private TextView basic_count,inter_count,advance_count;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        profile = findViewById(R.id.profile);


        basic_course = findViewById(R.id.basic_course);
        inter_course = findViewById(R.id.inter_course);
        advance_course = findViewById(R.id.advance_course);

        basic_count = findViewById(R.id.basic_lesson_count);
        inter_count = findViewById(R.id.inter_lesson_count);
        advance_count = findViewById(R.id.advance_lessons_count);

        mDatabase.child("BasicCourse").child("Lessons").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    basic_count.setText(String.valueOf(snapshot.getChildrenCount()) + " lesssons");
                    inter_count.setText(String.valueOf(snapshot.getChildrenCount()) + " lesssons");
                    advance_count.setText(String.valueOf(snapshot.getChildrenCount()) + " lesssons");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        basic_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home.this,Lessons.class);
                in.putExtra("title","Basic Course");
                startActivity(in);
            }
        });

        inter_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user.getSub_type().equals("PAID")){
                            Intent in = new Intent(Home.this,Lessons.class);
                            in.putExtra("title","Intermediate Course");
                            startActivity(in);
                        }
                        else{
                            new AlertDialog.Builder(Home.this)
                                    .setTitle("Locked!!!")
                                    .setMessage("Upgrad you Subscription")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("UPGRAD NOW", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent in = new Intent(Home.this, ProfileActivity.class);
                                            startActivity(in);
                                        }
                                    }).setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        advance_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user.getSub_type().equals("PAID")){
                            Intent in = new Intent(Home.this,Lessons.class);
                            in.putExtra("title","Advance Course");
                            startActivity(in);
                        }
                        else{
                            new AlertDialog.Builder(Home.this)
                                    .setTitle("Locked!!!")
                                    .setMessage("Upgrad you Subscription")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("UPGRAD NOW", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent in = new Intent(Home.this, ProfileActivity.class);
                                            startActivity(in);
                                        }
                                    }).setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Home.this,ProfileActivity.class);
                startActivity(profile);
            }
        });

        if(mAuth.getCurrentUser()==null){
            Intent home = new Intent(Home.this,LoginActivity.class);
            startActivity(home);
        }

        mDatabase.child("users").child(mAuth.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Toast.makeText(Home.this,"Welcome, "+user.getName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
