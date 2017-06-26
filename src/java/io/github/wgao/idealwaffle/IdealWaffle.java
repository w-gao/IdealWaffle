package io.github.wgao.idealwaffle;

import io.github.wgao.idealwaffle.applications.ApplicationManager;
import io.github.wgao.idealwaffle.events.EventExecutor;
import io.github.wgao.idealwaffle.events.core.SessionCreateEvent;
import io.github.wgao.idealwaffle.lang.Lang;
import io.github.wgao.idealwaffle.utils.IWLogger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class IdealWaffle {

    private static IWLogger Log = new IWLogger(IdealWaffle.class);

    private static IdealWaffle instance;

    private EventExecutor eventExecutor;

    private ApplicationManager applicationManager;

    private Session session;

    public IdealWaffle() {

    }

    public static IdealWaffle getInstance() {
        return instance;
    }

    public void initialize() {

        if (instance == null) {
            instance = this;
        }

        Log.info(Lang.get("LOADING_EVENT_EXECUTOR"));
        this.eventExecutor = new EventExecutor();

        Log.info(Lang.get("LOADING_APPLICATIONS"));

        String appPath = System.getProperty("user.dir") + "/applications";

        if (!new File(appPath).exists()) {
            new File(appPath).mkdirs();
        }
        this.applicationManager = new ApplicationManager();
        try {
            applicationManager.loadApplications(appPath);
        } catch (IOException e) {
            Log.warn(Lang.get("LOADING_APPLICATIONS_FAILED"));
        }
    }

    public boolean start() {

        SessionCreateEvent ev = new SessionCreateEvent(DummyClient.class);
        this.eventExecutor.execute(ev);

        Class<? extends Session> clazz = ev.getSessionClass();

        try {
            Constructor constructor = clazz.getConstructor();
            this.session = (Session) constructor.newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Log.error(Lang.get("SESSION_FAILED"));
            e.printStackTrace();
            return false;
        }

        this.session.init();

        // start the thread
        this.session.start();

        return true;
    }

    public void stop() {

        this.session.stop();

        this.applicationManager.unloadApplications();
        this.eventExecutor.unRegisterEvents();
    }

    public EventExecutor getEventExecutor() {
        return this.eventExecutor;
    }

    public Session getSession() {
        return this.session;
    }


}
