package space.wgao.idealwaffle.events;


import java.lang.reflect.Method;
import java.util.*;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */
public class EventExecutor {

    private Map<Class<?>, HandlerList> handlers = new HashMap<>();

    /**
     * Constructor of EventExecutor
     */
    public EventExecutor() {

    }

    /**
     * Register all the events in a listener
     *
     * @param listener EventListener
     */
    public void registerEvents(EventListener listener) {

        List<Method> methods = getEventMethods(listener);

        if (methods == null || methods.size() == 0) {
            return;
        }

        for (Method method : methods) {

            HandlerList list;

            Class<?> iev = method.getParameterTypes()[0];

            if (handlers.containsKey(iev)) {
                list = handlers.get(iev);
            } else {
                list = new HandlerList();
                handlers.put(iev, list);
            }

            final EventHandler handler = method.getAnnotation(EventHandler.class);
            list.add(new RegisteredListener(listener, method, handler.priority(), handler.ignoreCancelled()));
        }
    }

    /**
     * Unregister all the events
     */
    public void unRegisterEvents() {

        for (HandlerList handlerList : handlers.values()) {
            handlerList.removeListeners();
        }
    }

    /**
     * Unregister events from listener
     *
     * @param listener EventListener
     */
    public void unRegisterEvents(EventListener listener) {

        for (HandlerList handlerList : handlers.values()) {
            handlerList.removeListener(listener);
        }
    }

    /**
     * Execute an event
     *
     * @param event event
     */
    public void execute(Event event) {

        if (handlers.containsKey(event.getClass())) {
            handlers.get(event.getClass()).execute(event);
        }
    }

    /**
     * This gets all the event methods in 'listener' class
     *
     * @param listener listener
     * @return methods
     */
    private List<Method> getEventMethods(EventListener listener) {

        List<Method> validMethods = new LinkedList<>();

        Set<Method> methods;

        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0f);
            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, privateMethods);
        } catch (NoClassDefFoundError e) {
            return null;
        }

        for (final Method method : methods) {

            if (method.getAnnotation(EventHandler.class) == null) {
                continue;
            }

            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                continue;
            }

            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }

            method.setAccessible(true);

            validMethods.add(method);
        }
        return validMethods;
    }
}
