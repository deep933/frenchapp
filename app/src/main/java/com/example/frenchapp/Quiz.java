package com.example.frenchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
    private String hintt;
    private ProgressBar progressBar;

    private int q_no = 1;

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

        progressBar = findViewById(R.id.progress_bar);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);

        hint = findViewById(R.id.hint);

        showQuestion(q_no);

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hintt!=null){
                new AlertDialog.Builder(Quiz.this)
                        .setTitle("Hint !!")
                        .setMessage(hintt)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("GOT IT!!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }}
        });


    }

    private void showQuestion(int no){
        progressBar.setProgress(progressBar.getProgress()+10);
        question_no.setText("Question "+no);
        YoYo.with(Techniques.SlideInRight)
                .duration(700)
                .playOn(findViewById(R.id.quizcard));

        mDatabase.child("quizes").child("quiz1").child("q"+no).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Question q = snapshot.getValue(Question.class);
                question.setText(q.getQuestion());
                op1.setText(q.getOp1());
                op2.setText(q.getOp2());
                op3.setText(q.getOp3());
                hintt = q.getHint();

                op1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(1,q,op1);
                    }
                });
                op2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(2,q,op2);
                    }
                });
                op3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(3,q,op3);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

    private void checkAnswer(int i, Question q, final Button btn){
        if(i==q.getAns()){
            Toast.makeText(Quiz.this,"Correct",Toast.LENGTH_LONG).show();
            btn.setBackground(getResources().getDrawable(R.drawable.btn_green));
            btn.setEnabled(false);
            btn.setTextColor(getResources().getColor(R.color.green));

        }
        else{
            Toast.makeText(Quiz.this,"Incorrect",Toast.LENGTH_LONG).show();
            btn.setBackground(getResources().getDrawable(R.drawable.btn_red));
            btn.setEnabled(false);
            btn.setTextColor(getResources().getColor(R.color.red));


        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                btn.setEnabled(true);
                btn.setBackground(getResources().getDrawable(R.drawable.btn_default));
                btn.setTextColor(getResources().getColor(R.color.textPrimary));
                showQuestion(q_no+1);


            }

        }, 2000);
    }
}
