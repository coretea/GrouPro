
package com.groupro.groupro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;


public class Assignment_Dialog extends AppCompatDialogFragment {
    private EditText editAssignee;
    private EditText editTitle;
    private AssignmentDialogListener listener;
    private Button btn_date;
    private static Date due;
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
                        listener.createAssignment(asignee, title, due);
                    }
                });
        btn_date = (Button)view.findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
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
        void createAssignment(String asignee, String title, Date due);

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            return  dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            due = calendar.getTime();

        }
    }
}
