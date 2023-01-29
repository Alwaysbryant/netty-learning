package com.netty.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 服务端通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 创建一个选择器
        Selector selector = Selector.open();

        // 监听端口
        channel.socket().bind(new InetSocketAddress(18130));
        // 设置为非阻塞
        channel.configureBlocking(Boolean.FALSE);
        // 将服务端的channel注册到选择器中
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒无连接");
                continue;
            }
            // 获取所有注册的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            if (selectionKeys == null || selectionKeys.size() == 0) return;

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // selector 监听事件，如果当前的selectionKey是可接收新连接的，注册key对应的channel
                if (key.isAcceptable()) {
                    // 接收数据
                    SocketChannel socketChannel = channel.accept();
                    // 指定为非阻塞
                    socketChannel.configureBlocking(Boolean.FALSE);
                    // 注册channel
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                // 根据key从selector中获取到channel，并从中读取数据
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer attachment = (ByteBuffer) key.attachment();
                    socketChannel.read(attachment);
                    System.out.println("读取到客户端的数据" + new String(attachment.array()));
                }

                iterator.remove();
            }
        }


    }
}
