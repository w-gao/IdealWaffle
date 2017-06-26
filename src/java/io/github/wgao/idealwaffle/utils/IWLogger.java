package io.github.wgao.idealwaffle.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class IWLogger {

    private Logger Log = null;

    public IWLogger(Class<?> clazz) {
        Log = LogManager.getLogger(clazz);
    }

    public void debug(String s) {
        Log.debug("[*] " + s);
    }

    public void info(String s) {
        Log.info("[*] " + s);
    }

    public void info(String s, Object... objects) {
        Log.info("[*] " + s, objects);
    }

    public void warn(String s) {
        Log.warn("[!] " + s);
    }

    public void error(String s) {
        Log.error("[!] " + s);
    }

    public void fatal(String s) {
        Log.fatal("[!] " + s);
    }
}
