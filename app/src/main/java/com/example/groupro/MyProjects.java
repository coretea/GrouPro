package com.example.groupro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MyProjects extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);

        ImageView iv_new = findViewById(R.id.iv_new);
        iv_new.setOnClickListener(new View.OnClickListener() { //when clicking new button
            @Override
            public void onClick(View v) {
                
            }
        });

        ImageView iv_back = findViewById(R.id.iv_back);

    }
}
