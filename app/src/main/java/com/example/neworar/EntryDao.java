package com.example.neworar;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("SELECT * FROM entries ORDER BY start")
    List<Entry>getAllEntries();

    @Query("SELECT * FROM entries WHERE _id = :id")
    Entry getEntryById(int id);
}

