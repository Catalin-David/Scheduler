package com.example.neworar;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Entry.class}, version = 1)
public abstract class OrarDatabase extends RoomDatabase {

    public abstract EntryDao entryDao();

    private static OrarDatabase instance;

    public static synchronized OrarDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, OrarDatabase.class, "orar_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

