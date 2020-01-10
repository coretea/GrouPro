package com.example.groupro;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_login extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase db;
    DatabaseReference myRef;

    Button btn_signup;
    Button btn_login;
    EditText et_email;
    EditText et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        et_email = (EditText)findViewById(R.id.et_email);
        et_pass = (EditText)findViewById(R.id.et_pass);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

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
            Query query = myRef.child("users").orderByChild("email").equalTo(et_email.getText().toString().trim());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0

                        for (DataSnapshot dst : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            User u = dst.getValue(User.class);

                            if (u.getPassword().equals(et_pass.getText().toString().trim())) {
                                Toast.makeText(activity_login.this, "Welcome back, "+u.getName(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent( activity_login.this, activity_dashboard.class);
                                startActivity(intent);

                            } else
                                Toast.makeText(activity_login.this, "Wrong Password!", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(activity_login.this, "User not found!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
