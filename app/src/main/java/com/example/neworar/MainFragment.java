package com.example.neworar;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainFragment extends Fragment{
    private static final String TAG = "MainFragment";

    private BottomNavigationView bottomNavigationView;
    private TextView textDay;
    private ArrayList<Entry> entries;
    private static OrarDatabase database;
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private int day;
    private NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initWidgets(view);
        initBottomNavigation();
        initializeRecView();
        getCurrentDay();
        createSwipeGesture();
        return view;
    }

    private void initializeRecView(){
        adapter = new EntryAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = OrarDatabase.getInstance(getContext());
        entries = (ArrayList<Entry>) database.entryDao().getAllEntries();
    }

    private void createSwipeGesture(){
        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int SWIPE_MIN_DISTANCE = 100;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try{
                    if(Math.abs(e2.getY() - e1.getY()) > SWIPE_MAX_OFF_PATH){
                        return false;
                    }
                    if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                        onSwipeRightToLeft();
                    }else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                        onSwipeLeftToRight();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gesture.onTouchEvent(motionEvent);
            }
        });
    }

    void onSwipeRightToLeft(){
        Log.d(TAG, "onFling: Swipe right to left");
        switch (day){
            case 1:
                textDay.setText(R.string.tuesday);
                bottomNavigationView.setSelectedItemId(R.id.tuesday);
                updateRecView(2);
                break;
            case 2:
                textDay.setText(R.string.wednesday);
                bottomNavigationView.setSelectedItemId(R.id.wednesday);
                updateRecView(3);
                break;
            case 3:
                textDay.setText(R.string.thursday);
                bottomNavigationView.setSelectedItemId(R.id.thursday);
                updateRecView(4);
                break;
            case 4:
                textDay.setText(R.string.friday);
                bottomNavigationView.setSelectedItemId(R.id.friday);
                updateRecView(5);
                break;
            default:
               textDay.setText(R.string.monday);
                bottomNavigationView.setSelectedItemId(R.id.monday);
                updateRecView(1);
                break;
        }
    }

    void onSwipeLeftToRight(){
        Log.d(TAG, "onFling: Swipe left to right");
        switch (day){
            case 3:
                textDay.setText(R.string.tuesday);
                bottomNavigationView.setSelectedItemId(R.id.tuesday);
                updateRecView(2);
                break;
            case 4:
                textDay.setText(R.string.wednesday);
                bottomNavigationView.setSelectedItemId(R.id.wednesday);
                updateRecView(3);
                break;
            case 5:
                textDay.setText(R.string.thursday);
                bottomNavigationView.setSelectedItemId(R.id.thursday);
                updateRecView(4);
                break;
            case 1:
                textDay.setText(R.string.friday);
                bottomNavigationView.setSelectedItemId(R.id.friday);
                updateRecView(5);
                break;
            default:
                textDay.setText(R.string.monday);
                bottomNavigationView.setSelectedItemId(R.id.monday);
                updateRecView(1);
                break;
        }
    }

    private void getCurrentDay(){
        day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.TUESDAY:
                textDay.setText(R.string.tuesday);
                bottomNavigationView.setSelectedItemId(R.id.tuesday);
                updateRecView(2);
                break;
            case Calendar.WEDNESDAY:
                textDay.setText(R.string.wednesday);
                bottomNavigationView.setSelectedItemId(R.id.wednesday);
                updateRecView(3);
                break;
            case Calendar.THURSDAY:
                textDay.setText(R.string.thursday);
                bottomNavigationView.setSelectedItemId(R.id.thursday);
                updateRecView(4);
                break;
            case Calendar.FRIDAY:
                textDay.setText(R.string.friday);
                bottomNavigationView.setSelectedItemId(R.id.friday);
                updateRecView(5);
                break;
            default:
                textDay.setText(R.string.monday);
                bottomNavigationView.setSelectedItemId(R.id.monday);
                updateRecView(1);
                break;
        }
    }

    private void initWidgets(View view){
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        textDay = view.findViewById(R.id.txtDay);
        recyclerView = view.findViewById(R.id.recyclerView);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
    }

    private void updateRecView(int day){
        int current_week = MainActivity.get_week();
        this.day = day;
        entries = (ArrayList<Entry>) database.entryDao().getAllEntries();

        for(int i=0; i<entries.size(); i++){
            if(entries.get(i).getDay() != day) {
                entries.remove(i);
                i--;
            }else if(entries.get(i).getWeek() != 0){
                if(entries.get(i).getWeek() == 1 && current_week%2 != 1){
                    entries.remove(i);
                    i--;
                }else if(entries.get(i).getWeek() == 2 & current_week%2 != 0){
                    entries.remove(i);
                    i--;
                }
            }
        }
        adapter.setEntries(entries);
    }

    private void initBottomNavigation(){
        bottomNavigationView.setSelectedItemId(R.id.monday);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.monday:
                        textDay.setText(R.string.monday);
                        updateRecView(1);
                        break;
                    case R.id.tuesday:
                        textDay.setText(R.string.tuesday);
                        updateRecView(2);
                        break;
                    case R.id.wednesday:
                        textDay.setText(R.string.wednesday);
                        updateRecView(3);
                        break;
                    case R.id.thursday:
                        textDay.setText(R.string.thursday);
                        updateRecView(4);
                        break;
                    case R.id.friday:
                        textDay.setText(R.string.friday);
                        updateRecView(5);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
