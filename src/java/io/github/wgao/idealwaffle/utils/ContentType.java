package io.github.wgao.idealwaffle.utils;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public enum ContentType {

    TEXT("Text"),
    MAP("Map"),
    CARD("Card"),
    NOTE("Note"),
    SHARING("Sharing"),
    PICTURE("Picture"),
    RECORDING("Recording"),
    VOICE("Recording"),
    ATTACHMENT("Attachment"),
    VIDEO("Video"),
    FRIENDS("Friends"),
    SYSTEM("System");

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
