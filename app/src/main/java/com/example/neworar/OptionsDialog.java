package com.example.neworar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class OptionsDialog extends DialogFragment {

    private ConstraintLayout update, delete, cancel;
    private Entry entry;
    private OrarDatabase database;
    private int id;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_options, null);
        initWidgets(view);

        final Bundle bundle = getArguments();
        database = OrarDatabase.getInstance(getActivity());

        try{
            id = bundle.getInt("id");
            entry = database.entryDao().getEntryById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateEntryDialog dialog = new UpdateEntryDialog();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", id);
                dialog.setArguments(bundle1);
                dialog.show(getFragmentManager(), "update dialog");
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity())
                        .setTitle("Deleting entry")
                        .setMessage("Are you sure you want to delete " + entry.getName() + "?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.entryDao().delete(entry);
                            }
                        });
                builder1.create().show();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        return builder.create();
    }

    private void initWidgets(View view){
        update = view.findViewById(R.id.updateLayout);
        delete = view.findViewById(R.id.deleteLayout);
        cancel = view.findViewById(R.id.cancelLayout);
    }

}
