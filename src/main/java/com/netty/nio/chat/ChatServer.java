package com.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * <p>群聊系统服务端</p>
 */
public class ChatServer {

    private Selector selector;

    private ServerSocketChannel listener;

    private static final int PORT = 18160;

    public ChatServer() {
        try {
            this.selector = Selector.open();
            this.listener = ServerSocketChannel.open();
            listener.socket().bind(new InetSocketAddress(PORT));
            listener.configureBlocking(Boolean.FALSE);
            listener.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 监听
    @SuppressWarnings("all")
    public void listen() {
        try {
            while (true) {
                int select = selector.select(1000);
                if (select <= 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {
                        SocketChannel channel = listener.accept();
                        channel.configureBlocking(Boolean.FALSE);
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("客户端： " + channel.getRemoteAddress() + " 上线了");
                    }
                    if (next.isReadable()) {
                        readInfo(next);
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInfo(SelectionKey key) {
        SocketChannel channel = null;

        try {
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int read = channel.read(allocate);
            if (read > 0) {
                String info = new String(allocate.array());
                System.out.println("收到的消息内容: " + info);
                // 转发给其他客户端
                sendInfo(info, channel);
            }
        } catch (Exception e) {
            try {
                assert channel != null;
                System.out.println("客户端： " + channel.getRemoteAddress() + " 下线了");
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendInfo(String info, SocketChannel self) throws IOException {
        ByteBuffer buffer;
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && self != channel) {
                buffer = ByteBuffer.wrap(info.getBytes());
                SocketChannel writeChannel = (SocketChannel) channel;
                writeChannel.write(buffer);
            }
        }
    }



}
