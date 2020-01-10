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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        if(et_fullname.getText().toString().trim().length()== 0 || et_email.getText().toString().trim().length() == 0 ||
                et_pass.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "You must enter credentials to sign up!", Toast.LENGTH_LONG).show();
            finish();
            onBackPressed();
            return;
        }

        final String name = et_fullname.getText().toString();
        final String email = et_email.getText().toString();
        final String password = et_pass.getText().toString();


        Query q = myRef.child("users").orderByValue();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean userAlreadyExists = false;
                for (DataSnapshot dst: dataSnapshot.getChildren())
                {
                    User u = dst.getValue(User.class);
                    if (email.equals(u.getEmail()))
                    {
                        Toast.makeText(activity_register.this, "E-mail already registered!", Toast.LENGTH_LONG).show();
                        userAlreadyExists = true;
                    }
                }
                if (userAlreadyExists == false)
                {
                    register_user(email, name, password, dataSnapshot.getChildrenCount());
                    Toast.makeText(activity_register.this, "Welcome to GrouPro, "+name, Toast.LENGTH_LONG).show();
                    finish();
                    onBackPressed();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
