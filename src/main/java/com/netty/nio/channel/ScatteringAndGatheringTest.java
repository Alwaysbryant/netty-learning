package com.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 使用buffer数组
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.socket().bind(new InetSocketAddress(18271));

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        // 等待客户端链接
        SocketChannel accept = channel.accept();
        int length = 8;
        for (;;) {
            int read = 0;
            while (read < length) {
                long read1 = accept.read(byteBuffers);
                read += read1;
                System.out.println("累计读取的字节数:" + read);
                Arrays.stream(byteBuffers)
                        .map(buffer -> "position: " + buffer.position() + ",limit: " + buffer.limit())
                        .forEach(System.out::println);

            }
            // 反转buffer
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            // 显示在客户端
            long write = 0;
            while (write < length) {
                long write1 = accept.write(byteBuffers);
                write += write1;
            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);
        }

    }
}
