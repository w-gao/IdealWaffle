package io.github.wgao.idealwaffle.applications;

import io.github.wgao.idealwaffle.IdealWaffle;
import io.github.wgao.idealwaffle.events.EventExecutor;
import io.github.wgao.idealwaffle.events.EventListener;
import io.github.wgao.idealwaffle.utils.IWLogger;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public abstract class ApplicationBase {

    protected IWLogger Log = new IWLogger(this.getClass());

    private ApplicationInfo applicationInfo;


    public void load() {

    }

    public void unload() {

    }

    public void initialize(ApplicationInfo info) {
        this.applicationInfo = info;
    }

    public String getName() {
        return applicationInfo.name;
    }

    public String getAuthor() {
        return applicationInfo.author;
    }

    public String getDescription() {
        return applicationInfo.description;
    }

    public String getVersion() {
        return applicationInfo.version;
    }


    protected IdealWaffle getIdealWaffle() {
        return IdealWaffle.getInstance();
    }

    protected EventExecutor getEventExecutor() {
        return this.getIdealWaffle().getEventExecutor();
    }

    protected void registerEvents(EventListener listener) {
        this.getIdealWaffle().getEventExecutor().registerEvents(listener);
    }

}
