package com.malishalakshanrosa.tasktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class CreateTaskActivity extends AppCompatActivity {

    EditText et_task_name, et_deadline_date;
    NumberPicker np_hour, np_minute, np_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        et_task_name = findViewById(R.id.ET_task_name);
        et_deadline_date = findViewById(R.id.ET_deadline_date);
        np_hour = findViewById(R.id.h);
        np_minute = findViewById(R.id.m);
        np_second = findViewById(R.id.s);

        np_hour.setMinValue(0);
        np_hour.setMaxValue(24);

        np_minute.setMinValue(0);
        np_minute.setMaxValue(59);

        np_second.setMinValue(0);
        np_second.setMaxValue(59);


    }

    public void clear(){

        et_task_name.setText("");
        et_deadline_date.setText("");
        np_hour.setValue(0);
        np_minute.setValue(0);
        np_second.setValue(0);
//
//        int hours = np_hour.getValue();
//        int minutes = np_minute.getValue();
//        int seconds = np_second.getValue();


    }

    public void saveTask(View v){

        String taskName = et_task_name.getText().toString();
        String deadlineDate = et_deadline_date.getText().toString();

        int hours = np_hour.getValue();
        int minutes = np_minute.getValue();
        int seconds = np_second.getValue();

        TaskTimerHelper dbHelper = new TaskTimerHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO tasks (name, deadline, hour, minute, second, completedDate, complete) VALUES('"+taskName+"','"+deadlineDate+"','"+hours+"','"+minutes+"','"+seconds+"','"+null+"','0')";
        db.execSQL(sql);

        //4. Give a message
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();

        //5. Call clear function to clear EditText fields
        clear();

    }


}