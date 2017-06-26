package io.github.wgao.idealwaffle.commands;

import io.github.wgao.idealwaffle.IdealWaffle;
import io.github.wgao.idealwaffle.utils.IWLogger;

import java.util.Scanner;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class ConsoleCommandReader implements Runnable {

    private static IWLogger Log = new IWLogger(ConsoleCommandReader.class);

    private Scanner scanner = new Scanner(System.in);

    private IdealWaffle instance;

    public ConsoleCommandReader(IdealWaffle instance) {
        this.instance = instance;
    }

    public void run() {

        while (true) {
            String[] args = scanner.nextLine().trim().split(" ");
            String cmd = args[0];

            switch (cmd) {
                case "stop":
                case "exit":
                case "quit":
                case "":
                    this.instance.stop();
                    return;
                case "version":
                case "v":
                    Log.info("Version: v0.1");
                    break;
                case "send":

                    break;
                default:
                    Log.info("Command " + cmd + " not found.");
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
