
package com.example.groupro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;


public class Assignment_Dialog extends AppCompatDialogFragment {
    private EditText editAssignee;
    private EditText editTitle;
    private AssignmentDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.assignment_dialog, null);

        builder.setView(view)
                .setTitle("Assignment Creator")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String asignee = editAssignee.getText().toString();
                        String title = editTitle.getText().toString();
                        listener.createAssignment(asignee, title);
                    }
                });

        editTitle = view.findViewById(R.id.et_title);
        editAssignee = view.findViewById(R.id.et_asignee);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AssignmentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AssignmentDialogListener");
        }
    }

    public interface AssignmentDialogListener {
        Assignment createAssignment(String asignee, String title);
    }
}