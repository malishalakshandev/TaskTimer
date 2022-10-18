package com.malishalakshanrosa.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskTimerHelper extends SQLiteOpenHelper {


    public TaskTimerHelper(Context context){
        super(context,"dbTaskTimer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, deadline DATE, hour int, minute int, second int, completedDate DATE, complete BOOLEAN)";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

//        when you want to add new column to a table in DB in the second version realse. You need to use this
//        here, we have add new column named 'created_date' to 'task' table
//        and change the new version number in TaskHelper constructor to version parameter

//        if (oldVersion == 1 && newVersion == 2){
//
//            String sql1 = "ALTER TABLE tasks ADD COLUMN created_date DATE";
//            sqLiteDatabase.execSQL(sql1);
//
//        }

    }

}
