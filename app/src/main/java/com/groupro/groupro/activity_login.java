package com.groupro.groupro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_login extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase db;
    DatabaseReference myRef;

    Button btn_signup;
    Button btn_login;
    EditText et_email;
    EditText et_pass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startService(new Intent(this, BatteryService.class));

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

       mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(activity_login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(activity_login.this, activity_dashboard.class);
                    startActivity(i);
                }

            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onClick(View view) {

        if(view == btn_signup)
        {
            Intent intent = new Intent( activity_login.this, activity_register.class);
            startActivity(intent);
        }
        if(view == btn_login)
        {
            String email = et_email.getText().toString();
            String pwd = et_pass.getText().toString();
            if(email.isEmpty()){
                et_email.setError("Please enter email id");
                et_email.requestFocus();
            }
            else  if(pwd.isEmpty()){
                et_pass.setError("Please enter your password");
                et_pass.requestFocus();
            }
            else  if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(activity_login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
            }
            else  if(!(email.isEmpty() && pwd.isEmpty())){
                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(activity_login.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intToHome = new Intent(activity_login.this,activity_dashboard.class);
                            startActivity(intToHome);
                        }
                    }
                });
            }
            else{
                Toast.makeText(activity_login.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
