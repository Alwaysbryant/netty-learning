package com.netty.nio.groupchat;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        new ChatServer(7658).run();
    }
}
