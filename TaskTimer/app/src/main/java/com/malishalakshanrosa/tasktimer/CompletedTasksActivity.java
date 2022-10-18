package com.malishalakshanrosa.tasktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CompletedTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCompletedTasks();
    }

    public void toHome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loadCompletedTasks(){

        TaskTimerHelper dbHelper = new TaskTimerHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT _id, name, deadline, completedDate FROM tasks WHERE complete = '1' ";
        Cursor cursor = db.rawQuery(sql, null);

        //6. Get listView object
        ListView completedTaskListView = findViewById(R.id.LV_completed_task_list);

        int layout = R.layout.completed_one_task;
        String []columns = {"_id" ,"name", "deadline", "completedDate"};
        int[] labels = {R.id.completed_task_id, R.id.completed_task_name, R.id.completed_task_deadline, R.id.completed_on};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, layout, cursor, columns,labels);

        //8. Set adapter to the ListView
        completedTaskListView.setAdapter(adapter);
    }


}