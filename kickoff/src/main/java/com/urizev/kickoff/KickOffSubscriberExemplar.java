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
    final Method method;

    private KickOffSubscriberExemplar(Method method, String[] tags) {
        this.tags = tags;
        this.method = method;
    }

    KickOffSubscriber newSubscriber(Object subscriber) {
        return new KickOffSubscriber(this, subscriber);
    }

    static List<KickOffSubscriberExemplar> loadExemplars(Class clazz) {
        List<KickOffSubscriberExemplar> exemplars = new ArrayList<>();
        Method [] methods = clazz.getMethods();
        for (Method method : methods) {
            KOSubscribe annotation = method.getAnnotation(KOSubscribe.class);
            if (annotation == null) {
                continue;
            }
            String [] tags = annotation.value();
            if (tags.length == 0) {
                continue;
            }

            exemplars.add(new KickOffSubscriberExemplar(method, tags));
        }

        return exemplars;
    }
}
