package com.netty.groupchat;

public class Client {
    public static void main(String[] args) {
        new ChatClient("127.0.0.1", 7658).run();
    }
}
