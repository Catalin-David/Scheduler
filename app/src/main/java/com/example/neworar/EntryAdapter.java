package com.example.neworar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder>{

    public interface OnEntrySwipe{
        void onSwipingRecyclerViewItem(int direction);
    }

    private static FragmentActivity activity;
    private static ArrayList<Entry> entries;
    private OnEntrySwipe onEntrySwipe;

    public EntryAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtNumeMaterie.setText(entries.get(position).getName());
        holder.txtNumeProf.setText(entries.get(position).getTeacher());
        holder.txtSala.setText(entries.get(position).getRoom());
        holder.txtStart.setText(String.valueOf(entries.get(position).getStart()));
        holder.txtFinsh.setText(String.valueOf(entries.get(position).getFinish()));
        switch (entries.get(position).getType()){
            case 0:
                holder.parent.setBackgroundColor(Color.parseColor("#29c7ac"));
                break;
            case 1:
                holder.parent.setBackgroundColor(Color.parseColor("#fe346e"));
                break;
            case 2:
                holder.parent.setBackgroundColor(Color.parseColor("#65587f"));
                break;
            default:
                break;
        }
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                OptionsDialog dialog = new OptionsDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("id", entries.get(position).get_id());
                dialog.setArguments(bundle);
                dialog.show(activity.getSupportFragmentManager(), "options dialog");
                return false;
            }
        });
        final GestureDetector gesture = new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int SWIPE_MIN_DISTANCE = 100;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try{
                    if(Math.abs(e2.getY() - e1.getY()) > SWIPE_MAX_OFF_PATH){
                        return false;
                    }
                    onEntrySwipe = (OnEntrySwipe) activity;
                    if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                        onEntrySwipe.onSwipingRecyclerViewItem(1);
                    }else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                        onEntrySwipe.onSwipingRecyclerViewItem(2);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        holder.parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gesture.onTouchEvent(motionEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtStart, txtFinsh, txtNumeProf, txtNumeMaterie, txtSala;
        private ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStart = itemView.findViewById(R.id.txtStart);
            txtFinsh = itemView.findViewById(R.id.txtFinish);
            txtNumeMaterie = itemView.findViewById(R.id.txtNumeMaterie);
            txtNumeProf = itemView.findViewById(R.id.txtNumeProf);
            txtSala = itemView.findViewById(R.id.txtSala);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
