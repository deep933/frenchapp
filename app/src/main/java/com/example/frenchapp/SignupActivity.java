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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
                mAuth.createUserWithEmailAndPassword(user_email.getText().toString(), user_pass.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("trrt", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();



                                    // write new User
                                    writeNewUser(user);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

    private void writeNewUser(FirebaseUser user) {
        Toast.makeText(SignupActivity.this,user.getUid(),Toast.LENGTH_LONG).show();

        User u = new User(user_name.getText().toString(), user.getEmail().toString(),user.getUid());
        mDatabase.child("users").child(user.getUid()).setValue(u,  new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference dataReference) {
                Toast.makeText(SignupActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();


                Intent home = new Intent(SignupActivity.this,Home.class);
                startActivity(home);
            }
        });
    }


}
