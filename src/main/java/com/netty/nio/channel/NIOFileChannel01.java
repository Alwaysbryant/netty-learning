package com.netty.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 本地文件写
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        String content = "Hello, Java and Netty";

        FileOutputStream outputStream = new FileOutputStream("/Users/cyj/Desktop/file01.txt");
        FileChannel channel = outputStream.getChannel();
        /*
         * file ==>> buffer ==>> channel
         */
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(content.getBytes());

        // 转成读
        buffer.flip();

        channel.write(buffer);

        outputStream.close();

    }
}
