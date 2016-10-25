package com.urizev.lib.kickoffapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.urizev.kickoff.KickOff;
import com.urizev.kickoff.annotations.KOSubscribe;
import com.urizev.lib.kickoffapp.kickoff.task.Tasks;

public class MainActivity extends AppCompatActivity {
    private TextView task1;
    private TextView task2;
    private TextView task3;
    private TextView task4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task1 = (TextView) this.findViewById(R.id.task1);
        task2 = (TextView) this.findViewById(R.id.task2);
        task3 = (TextView) this.findViewById(R.id.task3);
        task4 = (TextView) this.findViewById(R.id.task4);

        KickOff.getDefault().subscribe(this);
    }

    @Override
    protected void onDestroy() {
        KickOff.getDefault().unsubscribe(this);
        super.onDestroy();
    }

    @KOSubscribe({Tasks.TASK1})
    public void onTask1Finished() {
        task1.setText("Terminada 1");
    }

    @KOSubscribe({Tasks.TASK2})
    public void onTask2Finished() {
        task2.setText("Terminada 2");
    }

    @KOSubscribe({Tasks.TASK3})
    public void onTask3Finished() {
        task3.setText("Terminada 3");
    }

    @KOSubscribe({Tasks.TASK1,Tasks.TASK2,Tasks.TASK3})
    public void onAllTaskFinished() {
        task4.setText("Terminadas todas");
    }
}
