package com.urizev.kickoff;

import android.os.AsyncTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por jcvallejo en 24/10/16.
 */
class KickOffProvider {
    private final Method loadMethod;
    private final List<KickOffSubscriber> subscribers;
    private final Object provider;
    boolean completed;
    private KickOffProviderTask task;

    KickOffProvider(Object provider, Method loadMethod) {
        this.provider = provider;
        this.loadMethod = loadMethod;
        this.subscribers = new ArrayList<>();
    }

    void subscribe(KickOffSubscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.addProvider(this);
    }

    void unsubscribe(KickOffSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    void provide() {
        if (completed) {
            return;
        }

        if (task != null) {
            return;
        }

        task = new KickOffProviderTask();
        task.execute();
    }

    private class KickOffProviderTask extends AsyncTask<Void,Void,Throwable> {
        @Override
        protected Throwable doInBackground(Void... params) {
            try {
                loadMethod.invoke(provider);
                return null;
            } catch (Throwable t) {
                return t;
            }
        }

        @Override
        protected void onPostExecute(Throwable throwable) {
            task = null;
            if (throwable == null) {
                completed = true;
                for (KickOffSubscriber subscriber : subscribers) {
                    subscriber.tryToPost();
                }
            }
            else {
                completed = false;
            }
        }
    }
}