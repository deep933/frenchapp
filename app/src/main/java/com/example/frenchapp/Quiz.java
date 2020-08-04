package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import org.w3c.dom.Text;

public class Quiz extends AppCompatActivity {

    private ImageView backbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView question_no;
    private Button op1,op2,op3,hint;
    private TextView question;

    private int count_correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        question = findViewById(R.id.question);
        question_no = findViewById(R.id.question_no);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);

        hint = findViewById(R.id.hint);

        showQuestion(1);


    }

    private void showQuestion(int no){
        mDatabase.child("quizes").child("quiz1").child("q"+no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Question q = snapshot.getValue(Question.class);
                question.setText(q.getQuestion());
                op1.setText(q.getOp1());
                op2.setText(q.getOp2());
                op3.setText(q.getOp3());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }
}
