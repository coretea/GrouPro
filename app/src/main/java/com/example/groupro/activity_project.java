package com.example.groupro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class activity_project extends AppCompatActivity implements Assignment_Dialog.AssignmentDialogListener {

    ListView lv_projects;
    List list = new ArrayList();
    ArrayAdapter adapter;
    Assignment assignment = new Assignment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Project project = new Project();
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
        RecyclerView rv_assignments = (RecyclerView)findViewById(R.id.rv_assignments);
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





    }

    public void openDialog() {
        Assignment_Dialog exampleDialog = new Assignment_Dialog();
        exampleDialog.show(getSupportFragmentManager(), "assignment dialog");
    }

    public Assignment createAssignment(String asignee, String title)
    {
        return new Assignment();
    }
}
