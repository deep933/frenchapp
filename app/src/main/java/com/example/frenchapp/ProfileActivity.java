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

import com.google.android.gms.tasks.OnSuccessListener;
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
    private Button upgrade;
    private boolean sub = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        score = findViewById(R.id.your_score);
        profile_Edit = findViewById(R.id.profile_edit);
        upgrade = findViewById(R.id.upgrade);

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("sub_type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    String sub_type = snapshot.getValue().toString();


                    if(sub_type.equals("FREE")){
                        sub =true;

                        upgrade.setText("Upgrade");
                    }
                    else{
                        sub = false;
                        upgrade.setText("Cancel Subscription");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sub) {
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("sub_type").setValue("PAID").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Subscription upgraded to premium", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("sub_type").setValue("FREE").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Subscription Canceled", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        mDatabase.child("Score").child(mAuth.getCurrentUser().getUid()).child("quiz_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {

                    long i =0;

                    if(snapshot!=null) {

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            i = i + (long) snap.getValue();
                        }
                    }

                    score.setText(String.valueOf(i));

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
