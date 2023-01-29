package com.netty.nio.chat;

public class ServerTest {

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        chatServer.listen();
    }
}
