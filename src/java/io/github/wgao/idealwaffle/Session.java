package io.github.wgao.idealwaffle;

import io.github.wgao.idealwaffle.lang.Lang;
import io.github.wgao.idealwaffle.utils.IWLogger;


/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public abstract class Session implements Runnable {

    private static IWLogger Log = new IWLogger(Session.class);

    private Thread thread = null;
    private boolean running = false;

    public Session() {

    }

    /**
     * Initialize all the necessary data
     * <p>
     * should pause here until user logged in
     * <p>
     * Note: this method is still using the main thread
     */
    public void init() {

        Log.info(Lang.get("SESSION_LOGGING_IN"));
        login();

        Log.info(Lang.get("SESSION_INITIALIZING"));
        wxinit();

    }

    public synchronized void start() {

        thread = new Thread(this, "session_thread");

        running = true;

        thread.start();
    }

    public synchronized void stop() {

        Log.info(Lang.get("SESSION_LOGGING_OUT"));
        logout();

        try {
            running = false;

            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main loop
     */
    public void run() {

        long timer = System.currentTimeMillis();
        int currTPS = 20;

        while (running) {
            this.tick();

            long duration = System.currentTimeMillis() - timer;
            if (duration < 1000 / currTPS) {
                try {
                    Thread.sleep((1000 / currTPS) - duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Log.warn("took too long (" + duration + ") to process tick().");
            }
            timer = System.currentTimeMillis();
        }

    }

    /**
     * Tick method - calls 20 times per second
     * <p>
     * 1. web wx status notify
     * 2. sync check
     */
    protected void tick() {

    }

    /**
     * login to the WeChat API
     * <p>
     * 1. fetch a UUID from the server
     * 2. load the QR code
     * 3. wait for user to scan & confirm login
     */
    public abstract void login();

    /**
     * Initialize the account
     * <p>
     * 1. get User account settings
     * 2. get Contacts
     * 3. batch get contact?
     */
    public abstract void wxinit();

    public abstract void logout();

    public void sendMessage() {

    }

    public void revokeMessage() {

    }

    protected void handleIncomingMessage() {

    }

    protected void handleModification() {

    }

}
