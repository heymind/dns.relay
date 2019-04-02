package dev.idx0;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world~");
        Server server = new Server(new InetSocketAddress("0.0.0.0",53),new InetSocketAddress("114.114.114.114",53));
        server.start();
    }
}
