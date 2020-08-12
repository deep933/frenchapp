package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private TextView loginbtn;
    private Button signupbtn;
    private ImageView backbtn;
    private EditText user_email,user_pass,user_name;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private LinearLayout sub;
    private Button sub_free,sub_paid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);
        backbtn = findViewById(R.id.backbtn);

        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        user_name = findViewById(R.id.user_name);

        sub = findViewById(R.id.sub_pop);
        sub.setVisibility(View.INVISIBLE);
        sub_free = findViewById(R.id.sub_free);
        sub_paid = findViewById(R.id.sub_paid);





        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(login);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub.setVisibility(View.VISIBLE);
            }
        });

        sub_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser("FREE");
            }
        });

        sub_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser("PAID");
            }
        });

    }

    private void writeNewUser(FirebaseUser user,String sub_type) {
        Toast.makeText(SignupActivity.this,user.getUid(),Toast.LENGTH_LONG).show();

        User u = new User(user_name.getText().toString(), user.getEmail().toString(),user.getUid(),sub_type);
        mDatabase.child("users").child(user.getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent home  = new Intent(SignupActivity.this,Home.class);
                startActivity(home);
            }
        });

        mDatabase.child("Score").child(user.getUid()).child("quiz_score").setValue("null");

    }

    private void registerUser(final String sub_type){
        mAuth.createUserWithEmailAndPassword(user_email.getText().toString(), user_pass.getText().toString())
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("trrt", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();



                            // write new User
                            writeNewUser(user,sub_type);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
