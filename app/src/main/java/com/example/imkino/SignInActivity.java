package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private Button BackToStart;
    private Button signUp;
    private Button signin;
    private EditText yourMail;
    private EditText yourPass;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        EditText EditTextLogIn =(EditText)findViewById(R.id.editTextLogIn);
        EditTextLogIn.setHintTextColor(Color.WHITE);

        EditText EditTextPass =(EditText)findViewById(R.id.editTextTextPassword);
        EditTextPass.setHintTextColor(Color.WHITE);

        String adminMail = "hasmikchiling2007@gmail.com";
        String adminPass = "hasulik200702";

        yourMail = (EditText) findViewById(R.id.editTextLogIn);
        yourPass = (EditText) findViewById(R.id.editTextTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        signUp = (Button) findViewById(R.id.buttonSignUpS);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
        signin = (Button) findViewById(R.id.buttonSignIn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = yourMail.getText().toString().trim();
                String pass = yourPass.getText().toString().trim();



                if(email.equals(" ") || pass.equals(" ") ){
                    Toast.makeText(SignInActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                }



                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if(email.equals(adminMail) && pass.equals(adminPass)){
                            openAdminActivity();
                        }else {
                            if (user != null) {
                                if (user.isEmailVerified()) {
                                    Toast.makeText(SignInActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),ListActivity.class));

                                } else {
                                    Toast.makeText(SignInActivity.this, "Pleas verify your email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignInActivity.this, "User is not signed in", Toast.LENGTH_SHORT).show();
                            }

                            if(task.isSuccessful()){
                            }else{
                                Toast.makeText(SignInActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
            }
        });

    }
    public void openActivitySignUp(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
    public void openListActivity(){
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
    public void openAdminActivity(){
        Intent intent = new Intent(this,AdminActivity.class);
        startActivity(intent);
    }

}