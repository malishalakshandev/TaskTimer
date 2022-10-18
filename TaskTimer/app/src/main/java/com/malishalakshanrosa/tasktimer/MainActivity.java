package com.malishalakshanrosa.tasktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toCreateTask(View v){

        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    public void toPendingTasks(View v){

        Intent intent = new Intent(this, PendingTasksActivity.class);
        startActivity(intent);
    }

    public void toCompletedTasks(View v){

        Intent intent = new Intent(this, CompletedTasksActivity.class);
        startActivity(intent);
    }
}