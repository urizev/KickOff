package com.urizev.kickoff;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Creado por jcvallejo en 24/10/16.
 */
class KickOffSubscriber {
    private final KickOffSubscriberExemplar exemplar;
    private final Object subscriber;
    private final Queue<KickOffProvider> providers;

    KickOffSubscriber(KickOffSubscriberExemplar exemplar, Object subscriber) {
        this.exemplar = exemplar;
        this.subscriber = subscriber;
        this.providers = new LinkedList<>();
    }

    void addProvider(KickOffProvider provider) {
        this.providers.add(provider);
    }

    private void invoke() {
        try {
            this.exemplar.method.invoke(subscriber);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void tryToPost() {
        boolean completed = true;
        for (KickOffProvider provider : providers) {
            if (!provider.completed) {
                completed = false;
                provider.provide();
            }
        }

        if (completed) {
            this.invoke();
        }
    }

    void unsubscribe() {
        while (! providers.isEmpty()) {
            KickOffProvider provider = providers.poll();
            provider.unsubscribe(this);
        }
    }
}
