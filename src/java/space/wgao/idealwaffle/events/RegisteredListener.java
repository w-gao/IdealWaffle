package space.wgao.idealwaffle.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */
class RegisteredListener {

    private EventListener listener;

    private Method method;

    private EventPriority priority;

    private boolean ignoreCancelled;

    RegisteredListener(EventListener listener, Method method, EventPriority priority, boolean ignoreCancelled) {
        this.listener = listener;
        this.method = method;
        this.priority = priority;

        this.ignoreCancelled = ignoreCancelled;
    }

    EventListener getListener() {
        return listener;
    }

    public EventPriority getPriority() {
        return priority;
    }

    void executeEvent(Event event) {

        if (event instanceof Cancellable) {
            if (((Cancellable) event).isCancelled() && !isIgnoringCancelled()) {
                return;
            }
        }

        try {
            this.method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // ignored
        }
    }

    private boolean isIgnoringCancelled() {
        return ignoreCancelled;
    }

}