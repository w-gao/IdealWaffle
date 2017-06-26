package io.github.wgao.idealwaffle.events;

import io.github.wgao.idealwaffle.lang.Lang;
import io.github.wgao.idealwaffle.utils.IWLogger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * IdealWaffle
 * <p>
 * EventHandlerList
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */

public class HandlerList {

    private static IWLogger Log = new IWLogger(HandlerList.class);

    private volatile Set<RegisteredListener> listeners = new HashSet<>();

    public HandlerList() {

    }

    /**
     * Executes the event by its priority order
     *
     * @param event event needs to be executed
     */
    public synchronized void execute(Event event) {

        for (Object listener : listeners.stream().sorted((x, y) -> y.getPriority().getSlot() - x.getPriority().getSlot()).toArray()) {

            ((RegisteredListener) listener).executeEvent(event);
        }
    }

    public synchronized void add(RegisteredListener listener) {

        if (!listeners.add(listener)) {
            Log.warn(Lang.get("EVENT_EXECUTOR_REGISTER_FAILED"));
        }
    }

    public synchronized void remove(RegisteredListener listener) {

        if (!listeners.remove(listener)) {
            Log.warn(Lang.get("EVENT_EXECUTOR_UN_REGISTER_FAILED"));
        }
    }

    public synchronized void removeListeners() {
        int removed = 0;
        int failed = 0;

        for (RegisteredListener l : listeners) {
            if (listeners.remove(l)) {
                removed++;
            } else {
                failed++;
            }
        }
        Log.info(Lang.get("EVENT_EXECUTOR_UN_REGISTER_SUCCESS", failed, removed));
    }

    public synchronized void removeListener(EventListener listener) {

        List<RegisteredListener> toRemove = listeners.stream().filter(l -> l.getListener().equals(listener)).collect(Collectors.toList());

        int removed = 0;
        int failed = 0;

        for (RegisteredListener l : toRemove) {
            if (listeners.remove(l)) {
                removed++;
            } else {
                failed++;
            }
        }
        Log.info(Lang.get("EVENT_EXECUTOR_UN_REGISTER_SUCCESS", failed, removed));
    }

}
