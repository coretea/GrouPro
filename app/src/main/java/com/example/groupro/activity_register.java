package com.example.groupro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_register extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase db;
    DatabaseReference myRef;
    static int users_counter = 0;

    EditText et_email;
    EditText et_fullname;
    EditText et_pass;
    Button btn_signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        et_email = (EditText)findViewById(R.id.et_email);
        et_fullname = (EditText)findViewById(R.id.et_fullname);
        et_pass = (EditText)findViewById(R.id.et_pass);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
    }



    private void register_user(String email, String name, String pass, long numUsers) {
        User newUser = new User(email, name, pass);
        myRef.child("users").child("user "+numUsers).setValue(newUser);
    }

    @Override
    public void onClick(View view) {
        String email = et_email.getText().toString();
        String pwd = et_pass.getText().toString();
        final String name = et_fullname.getText().toString();
        if(email.isEmpty()){
            et_email.setError("Please enter email id");
            et_email.requestFocus();
        }
        else  if(pwd.isEmpty()){
            et_pass.setError("Please enter your password");
            et_pass.requestFocus();
        }
        else  if(name.isEmpty()){
                et_fullname.setError("Please enter your full name");
                et_fullname.requestFocus();
        }
        else  if(!(email.isEmpty() && pwd.isEmpty() && name.isEmpty())){
            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(activity_register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(activity_register.this,"Sign Up was unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activity_register.this,"Welcome, "+name,Toast.LENGTH_SHORT).show();
                        finish();
                        onBackPressed();
                    }
                }
            });
        }
        else{
            Toast.makeText(activity_register.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

        }
    }
}
