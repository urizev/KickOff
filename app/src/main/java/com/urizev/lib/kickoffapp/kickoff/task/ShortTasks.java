package com.urizev.lib.kickoffapp.kickoff.task;

import android.util.Log;

import com.urizev.kickoff.annotations.KOProvide;

/**
 * Creado por jcvallejo en 24/10/16.
 */
public class ShortTasks {
    @KOProvide(Tasks.TASK1)
    public void task1() {
        Log.d("Task", "Ejecutando 1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Task", "Terminada 1");
    }

    @KOProvide(Tasks.TASK2)
    public void task2() {
        Log.d("Task", "Ejecutando 2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Task", "Terminada 2");
    }
}
