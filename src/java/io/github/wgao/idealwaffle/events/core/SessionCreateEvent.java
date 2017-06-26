package io.github.wgao.idealwaffle.events.core;

import io.github.wgao.idealwaffle.Session;
import io.github.wgao.idealwaffle.events.Event;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class SessionCreateEvent implements Event {

    private Class<? extends Session> sessionClass;

    public SessionCreateEvent(Class<? extends Session> sessionClass) {
        this.sessionClass = sessionClass;
    }

    public Class<? extends Session> getSessionClass() {
        return this.sessionClass;
    }

    public void setSessionClass(Class<? extends Session> sessionClass) {
        this.sessionClass = sessionClass;
    }
}
