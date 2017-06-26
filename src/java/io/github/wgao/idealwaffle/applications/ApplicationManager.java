package io.github.wgao.idealwaffle.applications;

import io.github.wgao.idealwaffle.lang.Lang;
import io.github.wgao.idealwaffle.utils.IWLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class ApplicationManager {

    private static IWLogger Log = new IWLogger(ApplicationManager.class);

    private final Map<String, ApplicationBase> apps = new HashMap<>();

    public void loadApplications(String dir) throws IOException {
        loadApplications(new File(dir));
    }

    public void loadApplications(File dir) throws IOException {

        for (File file : dir.listFiles()) {

            if (file.isDirectory() || !file.getName().endsWith(".jar")) {
                continue;
            }

            try {
                ApplicationBase app = loadApplication(file);

                if (app == null) continue;

                apps.put(app.getName(), app);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, ApplicationBase> getAllApplications() {
        return apps;
    }

    private ApplicationBase loadApplication(File file) throws Exception {

        Log.info(Lang.get("APPLICATION_LOAD_APP", file.getName().replace(".jar", "")));

        try (JarInputStream in = new JarInputStream(new FileInputStream(file))) {
            Manifest manifest = in.getManifest();
            if (manifest == null) {
                Log.error(Lang.get("APPLICATION_LOAD_FAILED_INVALID_MANIFEST"));
                return null;
            }

            String mainClass = manifest.getMainAttributes().getValue("Main-Class");

            URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()});

            ApplicationBase application;
            try {
                Class javaClass = loader.loadClass(mainClass);

                try {
                    Class appClass = javaClass.asSubclass(ApplicationBase.class);

                    application = (ApplicationBase) appClass.newInstance();

                    Application app = application.getClass().getAnnotation(Application.class);
                    if (app == null) {
                        return null;
                    }

                    application.initialize(new ApplicationInfo(app.Name(), app.Author(), app.Version(), app.Description()));
                    application.load();
                    Log.info(Lang.get("APPLICATION_LOAD_SUCCESS", app.Name(), app.Version()));
                    return application;

                } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
                    Log.warn(Lang.get("APPLICATION_LOAD_FAILED", file.getName()));
                }

            } catch (ClassNotFoundException ex) {
                Log.warn(Lang.get("APPLICATION_LOAD_FAILED_NO_MAIN_CLASS", mainClass));
            }
            return null;
        }
    }

    public void unloadApplications() {

        apps.forEach((s, app) -> {
            Log.info(Lang.get("APPLICATION_UNLOAD_APP", s));
            app.unload();
        });

        apps.clear();
    }
}
