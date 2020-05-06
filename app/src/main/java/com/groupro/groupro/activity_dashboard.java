package com.groupro.groupro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class activity_dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;

    ListView lv_assignments;
    List list = new ArrayList();
    ArrayAdapter adapter;
    final ArrayList<Assignment> user_assignments = new ArrayList<Assignment>();
    HashMap<String, Date> assignment_dates = new HashMap<String, Date>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CardView card_profile = (CardView) findViewById(R.id.card_profile); // creating a CardView and assigning a value.
        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(activity_dashboard.this, activity_profile.class);
                startActivity(intent_profile);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        CardView card_projects = (CardView) findViewById(R.id.card_projects); // creating a CardView and assigning a value.
        card_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_projects = new Intent(activity_dashboard.this, MyProjects.class);
                startActivity(intent_projects);
            }
        });

        CardView card_assignments = (CardView) findViewById(R.id.card_assignments); // creating a CardView and assigning a value.
        card_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_assignments = new Intent(activity_dashboard.this, activity_my_assignments.class);
                startActivity(intent_assignments);
            }
        });

        CardView card_due = (CardView) findViewById(R.id.card_due); // creating a CardView and assigning a value.
        CardView card_msg = (CardView) findViewById(R.id.card_msg); // creating a CardView and assigning a value.
        ImageView logout = (ImageView) findViewById(R.id.iv_logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finishAffinity();
                Intent intent_login = new Intent(activity_dashboard.this, activity_login.class);
                startActivity(intent_login);

            }
        });


        lv_assignments = (ListView)findViewById(R.id.lv_duesoon);

        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference("projects");
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
                        /*
                        String show = "   "+asn.getContent()+"\n   Due: "+asn.getDue().substring(0, (asn.getDue().length()- 17));
                        list.add(show);
                        */
                        Date date = new Date(asn.due);
                        assignment_dates.put(asn.content, date);
                        user_assignments.add(asn);
                    }
                }
                assignment_dates = sortByComparator(assignment_dates);
                for(Map.Entry<String, Date> entry :assignment_dates.entrySet()) {
                    String key = entry.getKey();
                    Date date = entry.getValue();
                    String date_str = date.toString();
                    String show = "   "+key+"\n   Due: "+date_str.substring(0, (date_str.length()- 17));
                    // do what you have to do here
                    // In your case, another loop.
                    list.add(show);
                }
                adapter = new ArrayAdapter<String>(activity_dashboard.this, android.R.layout.simple_list_item_1, list);
                lv_assignments.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });






    }

    public static HashMap<String,Date> sortByComparator(HashMap<String,Date> unsortMap) {

        List<Map.Entry<String,Date>> list = new LinkedList<Map.Entry<String,Date>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String,Date>> ()
        {
            public int compare(Map.Entry<String,Date> o1, Map.Entry<String,Date> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        HashMap<String,Date> sortedMap = new LinkedHashMap<String,Date>();
        for (Map.Entry<String,Date> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}



