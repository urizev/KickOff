package com.urizev.kickoff;

import com.urizev.kickoff.annotations.KOSubscribe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por jcvallejo en 24/10/16.
 */

class KickOffSubscriberExemplar {
    final String[] tags;
    private final Class clazz;
    final Method method;

    private KickOffSubscriberExemplar(Class clazz, Method method, String[] tags) {
        this.clazz = clazz;
        this.tags = tags;
        this.method = method;
    }

    public KickOffSubscriber newSubscriber(Object subscriber) {
        return new KickOffSubscriber(this, subscriber);
    }

    static List<KickOffSubscriberExemplar> loadExemplars(Class clazz) {
        List<KickOffSubscriberExemplar> exemplars = new ArrayList<>();
        Method [] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            KOSubscribe annotation = method.getAnnotation(KOSubscribe.class);
            if (annotation == null) {
                continue;
            }
            String [] tags = annotation.value();
            if (tags.length == 0) {
                continue;
            }

            exemplars.add(new KickOffSubscriberExemplar(clazz, method, tags));
        }

        return exemplars;
    }
}
