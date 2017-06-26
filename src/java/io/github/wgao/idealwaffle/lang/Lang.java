package io.github.wgao.idealwaffle.lang;

import io.github.wgao.idealwaffle.utils.Config;
import io.github.wgao.idealwaffle.utils.FileUtil;
import io.github.wgao.idealwaffle.utils.IWLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class Lang {

    private static IWLogger Log = new IWLogger(Lang.class);

    private static String LANG_PATH = "lang/" + Config.getProperty("Language", "en") + ".ini";

    private static Map<String, String> languages = new HashMap<>();

    public Lang() {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(LANG_PATH);

        if (stream == null) {
            Log.warn(String.format("Language: \"%s\" not found.", Config.getProperty("Language")));
            return;
        }

        try {
            String content = FileUtil.readFile(stream);
            Map<String, String> lang = new HashMap<>();

            if (!content.startsWith("# DO NOT REMOVE THIS LINE")) {
                return;
            }

            for (String line : content.split("\n")) {
                line = line.trim();
                if (line.equals("") || line.charAt(0) == '#') {
                    continue;
                }
                String[] t = line.split("=");
                if (t.length < 2) {
                    continue;
                }
                String key = t[0];
                String value = "";
                for (int i = 1; i < t.length - 1; i++) {
                    value += t[i] + "=";
                }
                value += t[t.length - 1];
                if (value.equals("")) {
                    continue;
                }
                lang.put(key, value);
            }
            languages = lang;

        } catch (IOException e) {
            Log.warn("Error while loading language: " + e.getMessage());
        }
    }

    public static String get(String key) {

        if (languages.containsKey(key)) {
            return languages.get(key);
        } else {
            return key;
        }
    }

    public static String get(String key, Object... args) {

        if (languages.containsKey(key)) {
            return String.format(languages.get(key), args);
        } else {
            return key;
        }
    }
}
