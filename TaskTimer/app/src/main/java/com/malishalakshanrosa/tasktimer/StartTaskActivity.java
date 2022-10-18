package com.malishalakshanrosa.tasktimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartTaskActivity extends AppCompatActivity {

    TextView tv_taskName;
    NumberPicker np_hour, np_minute, np_second;
    String TaskID;
    int h = 0;
    int m = 0;
    int s = 0;
    Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_task);

        tv_taskName = findViewById(R.id.TV_task_name);

        np_hour = findViewById(R.id.h);
        np_minute = findViewById(R.id.m);
        np_second = findViewById(R.id.s);

        np_hour.setMinValue(0);
        np_hour.setMaxValue(24);

        np_minute.setMinValue(0);
        np_minute.setMaxValue(59);

        np_second.setMinValue(0);
        np_second.setMaxValue(59);

        handle = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                np_hour.setValue(h);
                np_minute.setValue(m);
                np_second.setValue(s);

                if (m==0 && s==0){
                    // notify user
                    openTimerEndService();
                }
            }
        };

    }

    public void openTimerEndService(){
        Intent intent = new Intent(this, TimerEndService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TaskID = bundle.getString("TaskID");

        TextView tv_taskID = findViewById(R.id.task_id);
        tv_taskID.setText(TaskID);

        TaskTimerHelper dbHelper = new TaskTimerHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT _id, name, hour, minute, second FROM tasks WHERE complete = '0' AND _id = '"+ TaskID +"' ";
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        tv_taskName.setText(cursor.getString(1));
        np_hour.setValue(cursor.getInt(2));
        np_minute.setValue(cursor.getInt(3));
        np_second.setValue(cursor.getInt(4));

        cursor.close();
//        int h = cursor.getInt(2);
//        int m = cursor.getInt(3);
//        int s = cursor.getInt(4);

    }

    public void start(View v){

        Runnable r = new Runnable() {
            @Override
            public void run() {

                int HInS = np_hour.getValue()*3600;
                int MInS = np_minute.getValue()*60;
                int totalSeconds = HInS + MInS + np_second.getValue();

                for (int i = totalSeconds; i >= 0; i--){
                    try {
                        Thread.sleep(1000);
                        h = i/3600;
                        m = i/60;
                        s = i%60;

                        handle.sendEmptyMessage(0);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void done(View v){

        LinearLayout ll = (LinearLayout) v.getParent();
        TextView tv_TaskId = ll.findViewById(R.id.task_id);
        String taskId = tv_TaskId.getText().toString();

        //1. Create openHelper with Context passed
        TaskTimerHelper dbHelper = new TaskTimerHelper(this);

        //2. Get writtable database object
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        //3. Create insert SQL and execute
        String sql = "UPDATE tasks SET completedDate='"+today+"', complete=1 WHERE _id = '"+taskId+"'";
        db.execSQL(sql);

        Toast.makeText(this, "Task completed", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PendingTasksActivity.class);
        startActivity(intent);

    }

}