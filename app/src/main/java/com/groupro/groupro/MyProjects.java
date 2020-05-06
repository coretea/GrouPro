package com.groupro.groupro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyProjects extends AppCompatActivity {



    ListView lv_projects;
    List list = new ArrayList();
    ArrayAdapter adapter;
    final ArrayList<Project> user_projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference db_ref = db.getReference("projects");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // new button
        ImageView iv_new = findViewById(R.id.iv_new);
        iv_new.setOnClickListener(new View.OnClickListener() { //when clicking new button
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyProjects.this,activity_CreateProject.class);
                startActivity(i);
            }
        });
        // back button
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        // show list
        lv_projects = (ListView)findViewById(R.id.lv_projects);
        db_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project value=dataSnapshot.getValue(Project.class);
                if (value.getUsers().indexOf(mAuth.getCurrentUser().getDisplayName()) != -1)
                {
                    String show = "   "+value.getName()+"\n   Manager: "+value.getManager();
                    list.add(show);
                    adapter = new ArrayAdapter<String>(MyProjects.this, R.layout.simple_list_item_1, list);
                    lv_projects.setAdapter(adapter);
                    user_projects.add(value);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lv_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =  new Intent(MyProjects.this, activity_project.class);
                i.putExtra("Project", user_projects.get(position));
                startActivity(i);
            }
        });


    }


}
