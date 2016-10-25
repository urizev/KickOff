package com.urizev.lib.kickoffapp.kickoff.task;

import android.util.Log;

import com.urizev.kickoff.annotations.KOProvide;

/**
 * Creado por jcvallejo en 24/10/16.
 */
public class VeryLongTask {
    @KOProvide(Tasks.TASK3)
    public void task3() {
        Log.d("Task", "Ejecutando 3");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Task", "Terminada 3");
    }
}
