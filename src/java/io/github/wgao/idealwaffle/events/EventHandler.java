package io.github.wgao.idealwaffle.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * The priority of the event
     */
    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;
}