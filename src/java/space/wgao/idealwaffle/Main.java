package space.wgao.idealwaffle;

import space.wgao.idealwaffle.commands.ConsoleCommandReader;
import space.wgao.idealwaffle.lang.Lang;
import space.wgao.idealwaffle.utils.Config;


/**
 * IdealWaffle
 * <p>
 * Main class
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class Main {

    /**
     * Entry point of this program
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        new Config();

        new Lang();

        IdealWaffle instance = new IdealWaffle();
        instance.initialize();

        boolean success = instance.start();

        if (!success) {
            return;
        }

        System.out.println("Done! Type \"stop\" or \"exit\" to exit.");
        new Thread(new ConsoleCommandReader(instance)).start();
    }
}
