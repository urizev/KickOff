package com.urizev.kickoff;

import android.support.v4.util.ArrayMap;

import com.urizev.kickoff.annotations.KOProvide;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creado por jcvallejo en 24/10/16.
 */

public class KickOff {
    private static KickOff DEFAULT_INSTANCE;
    private final ArrayMap<String, KickOffProvider> providers;
    private final ArrayMap<Class, List<KickOffSubscriberExemplar>> subscriberExemplars;
    private final ArrayMap<Object, List<KickOffSubscriber>> subscribers;

    public static KickOff getDefault() {
        if (DEFAULT_INSTANCE == null) {
            DEFAULT_INSTANCE = new KickOff();
        }
        return DEFAULT_INSTANCE;
    }

    private KickOff() {
        this.providers = new ArrayMap<>();
        this.subscriberExemplars = new ArrayMap<>();
        this.subscribers = new ArrayMap<>();
    }

    public KickOff provide(Object provider) {
        Class clazz = provider.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            KOProvide annotation = method.getAnnotation(KOProvide.class);
            if (annotation != null) {
                String tag = annotation.value();
                providers.put(tag, new KickOffProvider(provider, method));
            }
        }
        return this;
    }

    public void subscribe(Object object) {
        Class clazz = object.getClass();
        List<KickOffSubscriberExemplar> exemplars = this.exemplarsForClass(clazz);
        List<KickOffSubscriber> subscribers = new ArrayList<>();
        for (KickOffSubscriberExemplar exemplar : exemplars) {
            KickOffSubscriber subscriber = exemplar.newSubscriber(object);
            for (String tag : exemplar.tags) {
                KickOffProvider provider = providers.get(tag);
                provider.subscribe(subscriber);
            }
            subscriber.tryToPost();
            subscribers.add(subscriber);
        }
        this.subscribers.put(object, subscribers);
    }

    public void unsubscribe(Object object) {
        List<KickOffSubscriber> subscribers = this.subscribers.remove(object);

        for (KickOffSubscriber subscriber : subscribers) {
            subscriber.unsubscribe();
        }
    }

    private List<KickOffSubscriberExemplar> exemplarsForClass(Class clazz) {
        List<KickOffSubscriberExemplar> exemplars = subscriberExemplars.get(clazz);
        if (exemplars == null) {
            exemplars = KickOffSubscriberExemplar.loadExemplars(clazz);
            subscriberExemplars.put(clazz, exemplars);
        }
        return exemplars;
    }
}
