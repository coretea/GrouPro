package com.groupro.groupro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.groupro.groupro.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class activity_CreateProject extends AppCompatActivity {
    String status = "";
    int budget = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__create_project);

        // vars
        ImageView iv_back = (ImageView)findViewById(R.id.iv_back);
        final EditText et_projectName = (EditText)findViewById(R.id.et_projectName);
        final EditText et_desc = (EditText)findViewById(R.id.et_desc);
        Button btn_continue = (Button)findViewById(R.id.btn_continue);
        final RadioGroup rg_status = (RadioGroup)findViewById(R.id.rg_status);
        final RadioButton rb_private = (RadioButton)findViewById(R.id.rb_private);
        final RadioButton rb_public = (RadioButton)findViewById(R.id.rb_public);
        final Button btn_budget = (Button)findViewById(R.id.btn_budget);
        //back button action
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }});

        btn_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBudgetDialog(activity_CreateProject.this);
            }});

        //continue button action
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_projectName.getText().toString();
                String description = et_desc.getText().toString();
                if(name.isEmpty()){
                    et_projectName.setError("Please enter a name for your project!");
                    et_projectName.requestFocus();
                }
                if (rg_status.getCheckedRadioButtonId() == -1)
                {
                    int lastChildPos=rg_status.getChildCount()-1;
                    ((RadioButton)rg_status.getChildAt(lastChildPos)).setError("You must check one of them");
                    rg_status.requestFocus();
                }
                else{
                    if (rb_private.isChecked())
                        status = "Private";
                    if (rb_public.isChecked())
                        status = "Public";
                 }
                ;
                ArrayList<User> allUsers = User.getUserList();
                ArrayList<String> projUsers = new ArrayList<String>();
                projUsers.add(mAuth.getCurrentUser().getDisplayName());
                // create project class
                Project new_proj = new Project(name, mAuth.getCurrentUser().getDisplayName(), budget, projUsers, status, description);
                // add to db
                new_proj.addProjectToDB(new_proj);
            }});

    }

    private void showBudgetDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        taskEditText.setKeyListener(DigitsKeyListener.getInstance(null, false, true));  //  positive decimal numbers
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Budget Selector")
                .setMessage("Enter your budget")
                .setView(taskEditText)
                .setPositiveButton("Set Budget", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       budget = Integer.parseInt(String.valueOf(taskEditText.getText()));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
