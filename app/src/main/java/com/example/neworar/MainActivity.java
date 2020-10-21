package com.example.neworar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, InsertEntryDialog.addEntry,
        UpdateEntryDialog.updateEntry, EntryAdapter.OnEntrySwipe{

    @Override
    public void onSwipingRecyclerViewItem(int direction) {
        if(direction == 2){
            fragment.onSwipeLeftToRight();
        }else{
            fragment.onSwipeRightToLeft();
        }
    }

    @Override
    public void onUpdatingEntry(Entry entry) {
        OrarDatabase database = OrarDatabase.getInstance(this);
        database.entryDao().update(entry);
    }

    @Override
    public void onAddingEntry(Entry entry) {
        OrarDatabase database = OrarDatabase.getInstance(this);
        database.entryDao().insert(entry);
    }

    public static int get_week(){
        return current_week;
    }

    public static int current_week;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView txtView;
    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        current_week = sharedPreferences.getInt("week", 1);


        initViews();
        txtView.setText("Week " + current_week);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment).commit();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("week", current_week);
        editor.apply();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert:
                new InsertEntryDialog().show(getSupportFragmentManager(), "insert entry dialog");
                break;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("About the developer")
                        .setMessage("My name is David Catalin Ioan and I am currently studying Computer Science " +
                                "at Babes-Bolyai University Cluj Napoca. I have been learning about Android for about " +
                                "8 months at the time of writing this message. My interest for this area has been " +
                                "steadily growing and I want to take this hobby to the next level. Thus, I am " +
                                "available for hiring. You can contact me at cata02dav@yahoo.com. If you want, you " +
                                "can check other projects of mine at my Github page.")
                        .setPositiveButton("Take me to github!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("I'm good mate!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        });
                builder.create().show();
                break;
            case R.id.next_week:
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                current_week = sharedPreferences.getInt("week", 1);
                current_week = min(14,current_week+1);
                txtView.setText("Week " + current_week);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("week", current_week);
                editor.apply();
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment_container, new MainFragment()).commit();
                break;
            case R.id.prev_week:
                SharedPreferences sharedPreferences1 = getPreferences(Context.MODE_PRIVATE);
                current_week = sharedPreferences1.getInt("week", 1);
                current_week = max(1, current_week-1);
                txtView.setText("Week " + current_week);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putInt("week", current_week);
                editor1.apply();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.fragment_container, new MainFragment()).commit();
                break;
            default:
                break;
        }
        return true;
    }

    private void initViews(){
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationDrawer);
        txtView = findViewById(R.id.txtViewWeek);
    }

}
