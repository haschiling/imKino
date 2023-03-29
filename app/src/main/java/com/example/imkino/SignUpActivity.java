package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private Button signInDone;
    FirebaseAuth firebaseAuth;
    private EditText editTextMail;
    private EditText editTextPass;
    private EditText editTextRepPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText EditTextPassL =(EditText)findViewById(R.id.editTextPassword);
        EditTextPassL.setHintTextColor(Color.WHITE);

        EditText EditTextMailL =(EditText)findViewById(R.id.editTextMailL);
        EditTextMailL.setHintTextColor(Color.WHITE);

        EditText EditTextRepPass =(EditText)findViewById(R.id.editTextRepPassword);
        EditTextRepPass.setHintTextColor(Color.WHITE);

        editTextMail = (EditText) findViewById(R.id.editTextMailL);
        editTextPass = (EditText) findViewById(R.id.editTextPassword);
        editTextRepPass = (EditText) findViewById(R.id.editTextRepPassword);
        signInDone = (Button) findViewById(R.id.buttonSignUp);


        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            finish();
        }

        signInDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextMail.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();
                String repPass = editTextRepPass.getText().toString().trim();

                if(email.equals(" ") || pass.equals(" ") || repPass.equals(" ")){
                    Toast.makeText(SignUpActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                }



                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                                        }else {
                                            Toast.makeText(SignUpActivity.this, "Pleas verify your email", Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(SignUpActivity.this, "User is created. Pleas check your email for verification", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }else {
                            Toast.makeText(SignUpActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}