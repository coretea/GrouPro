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
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
            }
        });
        mAuth = FirebaseAuth.getInstance();
        CardView card_projects = (CardView) findViewById(R.id.card_projects); // creating a CardView and assigning a value.

        CardView card_assignments = (CardView) findViewById(R.id.card_assignments); // creating a CardView and assigning a value.
        CardView card_due = (CardView) findViewById(R.id.card_due); // creating a CardView and assigning a value.
        CardView card_msg = (CardView) findViewById(R.id.card_msg); // creating a CardView and assigning a value.
        ImageView logout = (ImageView)findViewById(R.id.iv_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser == null ){
                    activity_dashboard.super.onBackPressed();
                    finish();
                    onBackPressed();
                }

            }
        });
    }


}
