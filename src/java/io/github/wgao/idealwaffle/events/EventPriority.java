package io.github.wgao.idealwaffle.events;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */

public enum EventPriority {

    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4),
    MONITOR(5);

    private final int slot;

    EventPriority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}