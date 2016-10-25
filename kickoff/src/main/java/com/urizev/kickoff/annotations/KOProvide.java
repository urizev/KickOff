package com.urizev.kickoff.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Creado por jcvallejo en 24/10/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface KOProvide {
    String value() default "";
}
