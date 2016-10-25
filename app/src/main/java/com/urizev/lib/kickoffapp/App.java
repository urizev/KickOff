package com.urizev.lib.kickoffapp;

import android.app.Application;

import com.urizev.kickoff.KickOff;
import com.urizev.lib.kickoffapp.kickoff.task.ShortTasks;
import com.urizev.lib.kickoffapp.kickoff.task.VeryLongTask;

/**
 * Creado por jcvallejo en 24/10/16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KickOff.getDefault()
                .provide(new ShortTasks())
                .provide(new VeryLongTask());
    }
}
