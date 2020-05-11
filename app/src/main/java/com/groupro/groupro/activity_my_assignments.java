package com.groupro.groupro;

import android.os.Bundle;
import android.view.View;
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

/**
 * this activity shows the user the activity which he is assigned for.
 */
public class activity_my_assignments extends AppCompatActivity {

    ListView lv_assignments;
    List list = new ArrayList();
    ArrayAdapter adapter;
    final ArrayList<Assignment> user_assignments = new ArrayList<Assignment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assignments);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference db_ref = db.getReference("projects");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ImageView iv_back = (ImageView)findViewById(R.id.iv_back);
        lv_assignments = (ListView)findViewById(R.id.lv_assignments);

        // back button
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        // show list
        db_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project value = dataSnapshot.getValue(Project.class);
                ArrayList<Assignment> proj_assignments = value.getAssignments();
                for (int i =0; i < proj_assignments.size(); i++)
                {
                    Assignment asn = proj_assignments.get(i);
                    if (mAuth.getCurrentUser().getEmail().equals(asn.asignee))
                    {
                        String show = "   "+asn.getContent()+"\n   Due: "+asn.getDue().substring(0, (asn.getDue().length()- 17));
                        list.add(show);
                        adapter = new ArrayAdapter<String>(activity_my_assignments.this, R.layout.simple_list_item_1, list);
                        lv_assignments.setAdapter(adapter);
                        user_assignments.add(asn);
                    }
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

    }
}
