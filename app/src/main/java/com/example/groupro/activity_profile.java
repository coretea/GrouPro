package com.example.groupro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_profile extends AppCompatActivity {
    TextView tv_email;
    TextView tv_name;
    ImageView iv_profile;
    ImageView iv_back;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_name = (TextView)findViewById(R.id.tv_name);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mainSnapshot) {

                User userProfile = mainSnapshot.getValue(User.class);
                tv_name.setText(userProfile.getName().toString());
                tv_email.setText(userProfile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }

        });



        iv_profile = (ImageView)findViewById(R.id.iv_profile);
        iv_back = (ImageView)findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
    }






}
