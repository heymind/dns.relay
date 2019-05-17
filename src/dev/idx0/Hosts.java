package dev.idx0;


import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Hosts {
    private Map<String, String> hosts = new HashMap<>();

    Hosts(String filePath) throws IOException {
        for (String line : Files.readAllLines(Paths.get(filePath))) {
            String[] hostAndIp = line.split("->");
            assert hostAndIp.length == 2;
            hosts.put(hostAndIp[0].toUpperCase(), hostAndIp[1]);
        }
    }

    String get(String host) {
        return hosts.get(host.toUpperCase());
    }
}
