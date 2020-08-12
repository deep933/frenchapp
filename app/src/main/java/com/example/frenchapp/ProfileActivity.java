package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView user_name,user_email;
    private Button logout;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ImageView backbtn;
    private Button profile_Edit;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        score = findViewById(R.id.score);
        profile_Edit = findViewById(R.id.profile_edit);

        mDatabase.child("Score").child(mAuth.getCurrentUser().getUid()).child("quiz_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
Toast.makeText(ProfileActivity.this,String.valueOf(snap.getValue()),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        profile_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ProfileActivity.this,ProfileEdit.class);
                startActivity(in);
            }
        });



        backbtn = findViewById(R.id.backbtn);


        logout = findViewById(R.id.logoutbtn);

        if(mAuth.getCurrentUser()==null){
            Intent home = new Intent(ProfileActivity.this,LoginActivity.class);
            startActivity(home);
        }

        mDatabase.child("users").child(mAuth.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                user_email.setText(user.getEmail());
                user_name.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent home = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(home);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
