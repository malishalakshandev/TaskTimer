package com.malishalakshanrosa.tasktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PendingTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_tasks);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPendingTasks();
    }

    public void toHome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loadPendingTasks(){

        TaskTimerHelper dbHelper = new TaskTimerHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT _id, name, deadline, hour, minute, second FROM tasks WHERE complete = '0' ";
        Cursor cursor = db.rawQuery(sql, null);


        //6. Get listView object
        ListView pendingTaskListView = findViewById(R.id.LV_pending_task_list);


        int layout = R.layout.pending_one_task;
        String []columns = {"_id","name", "deadline","hour", "minute", "second"};
        int[] labels = {R.id.pending_task_id, R.id.pending_task_name, R.id.pending_task_deadling, R.id.pending_task_remain_hours, R.id.pending_task_remain_minutes, R.id.pending_task_remain_seconds};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, layout, cursor, columns,labels);

        //8. Set adapter to the ListView
        pendingTaskListView.setAdapter(adapter);

    }

    public void toStartTask(View v){

        LinearLayout ll = (LinearLayout) v.getParent();
        TextView tv_taskID = ll.findViewById(R.id.pending_task_id);
        String taskID = tv_taskID.getText().toString();

        Intent intent = new Intent(this, StartTaskActivity.class);
        intent.putExtra("TaskID", taskID);
        startActivity(intent);

    }


}