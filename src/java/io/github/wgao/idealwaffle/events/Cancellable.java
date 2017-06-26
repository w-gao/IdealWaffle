package io.github.wgao.idealwaffle.events;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2017
 * @version 1.0
 */
public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean value);
}
