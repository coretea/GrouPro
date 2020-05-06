package com.groupro.groupro;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class activity_project extends AppCompatActivity implements Assignment_Dialog.AssignmentDialogListener {

    List list = new ArrayList();
    ArrayAdapter adapter;
    Assignment assignment = new Assignment();
    static Project project = new Project();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        if(getIntent().getExtras() != null) {
            project = (Project) getIntent().getSerializableExtra("Project");
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference db_ref = db.getReference("projects");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView tv_budget = (TextView)findViewById(R.id.tv_budgetvalue);
        TextView tv_manager = (TextView)findViewById(R.id.tv_managervalue);
        TextView tv_description = (TextView)findViewById(R.id.tv_descriptionvalue);
        TextView tv_name = (TextView)findViewById(R.id.tv_myprojects);
        ListView lv_assignments = (ListView)findViewById(R.id.lv_assignments);
        ImageView iv_back = (ImageView)findViewById(R.id.iv_back);
        ImageView iv_assignments = (ImageView)findViewById(R.id.iv_assignments);

        iv_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });


        String budget = String.valueOf(project.getBudget());
        tv_budget.setText(budget);
        tv_description.setText(project.getDescription());
        tv_manager.setText(project.getManager());
        tv_name.setText(project.getName());


        for (int i = 1; i < project.getAssignments().size(); i ++)
        {
            Assignment asn = project.getAssignments().get(i);
            String show = asn.getContent()+"\nAssignee: "+asn.asignee+"\nDue: "+asn.getDue().substring(0, (asn.getDue().length()- 17));
            list.add(show);
        }
        adapter = new ArrayAdapter<String>(activity_project.this, android.R.layout.simple_list_item_1, list);
        lv_assignments.setAdapter(adapter);




    }

    public void openDialog() {
        Assignment_Dialog exampleDialog = new Assignment_Dialog();
        exampleDialog.show(getSupportFragmentManager(), "assignment dialog");
    }

    public void createAssignment(String asignee, String title, Date due)
    {
        assignment = new Assignment(asignee, title, due.toString());
        project.addAssigment(assignment);
        project.addProjectToDB(project);
    }
}
