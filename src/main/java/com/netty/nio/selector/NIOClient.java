package com.netty.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(Boolean.FALSE);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 18130);

        // 连接服务器
        if (!socketChannel.connect(address)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("正在连接服务器");
            }
        }

        String content = "Hello, Java nio";
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
        socketChannel.write(buffer);
    }
}
