package dev.idx0;

import lombok.extern.java.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Log
public class Main {
    public static void setLoggerLevel(Level level){
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }
    }
    public static void main(String[] args) throws IOException {

        String upstream = "202.106.0.20";
        String hostsFile = "./hosts";
        int argAt = 0;
        if (args.length > 0) {
            if (args[0].equals("-d")) {
                setLoggerLevel(Level.INFO);
                argAt++;
            } else if (args[0].equals("-dd")) {
                setLoggerLevel(Level.FINE);
                argAt++;
            } else {
                setLoggerLevel(Level.WARNING);
            }
        }
        if (args.length - argAt > 0) {
            upstream = args[argAt];
            argAt++;
        }
        if (args.length - argAt > 0) {
            hostsFile = args[argAt];
            argAt++;
        }

        log.warning(String.format("Start DNS relay server @0.0.0.0, upstream %s , hosts %s",upstream,hostsFile));

        Server server = new Server(
                new InetSocketAddress("0.0.0.0", 53),
                new InetSocketAddress(upstream, 53),
                new Hosts(hostsFile));
        server.start();
    }
}
