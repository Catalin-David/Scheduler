package com.example.neworar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateEntryDialog extends DialogFragment {

    public interface updateEntry{
        void onUpdatingEntry(Entry entry);
    }

    private updateEntry updateEntry;
    private EditText name, teacher, room, start, finish;
    private Spinner tip, week, day;
    private Button btnAdd;
    private int id;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_insert_entry, null);
        initWidgets(view);

        Bundle bundle = getArguments();
        id = bundle.getInt("id");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Updating entry")
                .setView(view);

        btnAdd.setText("Update entry");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEntry();
                dismiss();
            }
        });
        return builder.create();
    }

    private void updateEntry(){
        Entry entry = new Entry();
        entry.set_id(id);
        entry.setName(name.getText().toString());
        entry.setTeacher(teacher.getText().toString());
        entry.setRoom(room.getText().toString());
        entry.setStart(Integer.valueOf(start.getText().toString()));
        entry.setFinish(Integer.valueOf(finish.getText().toString()));
        String type = tip.getSelectedItem().toString();
        if(type.equalsIgnoreCase("curs")){
            entry.setType(0);
        }else if(type.equalsIgnoreCase("seminar")){
            entry.setType(1);
        }else if(type.equalsIgnoreCase("laborator")){
            entry.setType(2);
        }
        String saptamana = week.getSelectedItem().toString();
        if(saptamana.equalsIgnoreCase("weekly")){
            entry.setWeek(0);
        }else if(saptamana.equalsIgnoreCase("sapt1")){
            entry.setWeek(1);
        }else if(saptamana.equalsIgnoreCase("sapt2")){
            entry.setWeek(2);
        }

        String ziua = day.getSelectedItem().toString();
        if(ziua.equalsIgnoreCase("monday")){
            entry.setDay(1);
        }else if(ziua.equalsIgnoreCase("tuesday")){
            entry.setDay(2);
        }else if(ziua.equalsIgnoreCase("wednesday")){
            entry.setDay(3);
        }else if(ziua.equalsIgnoreCase("thursday")){
            entry.setDay(4);
        }else if(ziua.equalsIgnoreCase("friday")){
            entry.setDay(5);
        }

        updateEntry = (updateEntry) getActivity();
        updateEntry.onUpdatingEntry(entry);
    }

    void initWidgets(View view){
        name = view.findViewById(R.id.subjectName);
        teacher = view.findViewById(R.id.teacherName);
        room = view.findViewById(R.id.subjectRoom);
        start = view.findViewById(R.id.startHour);
        finish = view.findViewById(R.id.endHour);
        tip = view.findViewById(R.id.spinnerTip);
        week = view.findViewById(R.id.spinnerWeek);
        day = view.findViewById(R.id.spinnerDay);
        btnAdd = view.findViewById(R.id.btnAddEntry);
    }
}
