package com.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 客户端
 */
public class ChatClient {
    private Selector selector;

    private SocketChannel channel;

    private static final int PORT = 18160;

    private String username;

    public ChatClient() {
        try {
            selector = Selector.open();
            channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            channel.configureBlocking(Boolean.FALSE);
            channel.register(selector, SelectionKey.OP_READ);
            username = channel.getLocalAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(String info) {
        String content = username + ": " + info;
        ByteBuffer wrap = ByteBuffer.wrap(content.getBytes());
        try {
            channel.write(wrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        channel.read(allocate);
                        String content = new String(allocate.array());
                        System.out.println("收到消息： " + content.trim());
                    }
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
