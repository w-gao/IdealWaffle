package space.wgao.idealwaffle.utils;

import java.io.*;
import java.util.Properties;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2016
 * @version 1.0
 */
public class Config {

    private static final String ConfigFile = "config.properties";

    private static Properties properties;

    public Config() {
        properties = new Properties();

        String path = System.getProperty("user.dir") + "/" + ConfigFile;
        File file = new File(path);
        if (!file.exists()) {
            try {
                InputStream stream = this.getClass().getClassLoader().getResourceAsStream(ConfigFile);
                FileUtil.writeFile(file, stream);
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }

        // load properties...
        try {
            FileInputStream stream = new FileInputStream(ConfigFile);

            properties.load(stream);
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean getPropertyBoolean(String key, Object defaultValue) {
        Object value = getProperty(key, defaultValue.toString());

        switch (String.valueOf(value).toLowerCase()) {
            case "on":
            case "true":
            case "1":
            case "yes":
                return true;
        }
        return false;
    }

    public static int getPropertyInt(String key, Integer defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static String getProperty(String key) {

        return getProperty(key, "");
    }

    public static String getProperty(String key, String defaultValue) {

        if (properties == null) return "null";

        return properties.getProperty(key, defaultValue);
    }
}
