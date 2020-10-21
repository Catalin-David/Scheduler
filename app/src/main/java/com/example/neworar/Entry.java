package com.example.neworar;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "entries")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private String name;
    private int type;   // 0-> curs, 1-> seminar, 2-> laborator
    private String teacher;
    private String room;
    private int week;   // 0-> all, 1 -> odd, 2-> even
    private int day; // we start from 1
    private int start;
    private int finish;


    public Entry(String name, int type, String teacher, String room, int week, int day, int start, int finish) {
        this.name = name;
        this.type = type;
        this.teacher = teacher;
        this.room = room;
        this.week = week;
        this.day = day;
        this.start= start;
        this.finish = finish;
    }
    @Ignore
    public Entry(){

    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() { return _id; }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }

    public int getStart() {
        return start;
    }

    public int getFinish() {
        return finish;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
