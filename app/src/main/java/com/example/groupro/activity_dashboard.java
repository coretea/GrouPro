package com.example.groupro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CardView card_profile = (CardView) findViewById(R.id.card_profile); // creating a CardView and assigning a value.
        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(activity_dashboard.this,activity_profile.class);
                startActivity(intent_profile);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        CardView card_projects = (CardView) findViewById(R.id.card_projects); // creating a CardView and assigning a value.
        card_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_projects = new Intent(activity_dashboard.this,MyProjects.class);
                startActivity(intent_projects);
            }
        });

        CardView card_assignments = (CardView)findViewById(R.id.card_assignments); // creating a CardView and assigning a value.
        card_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_assignments = new Intent(activity_dashboard.this,activity_my_assignments.class);
                startActivity(intent_assignments);
            }
        });

        CardView card_due = (CardView) findViewById(R.id.card_due); // creating a CardView and assigning a value.
        CardView card_msg = (CardView) findViewById(R.id.card_msg); // creating a CardView and assigning a value.
        ImageView logout = (ImageView)findViewById(R.id.iv_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finishAffinity();
                Intent intent_login = new Intent(activity_dashboard.this,activity_login.class);
                startActivity(intent_login);

            }
        });
    }


}
