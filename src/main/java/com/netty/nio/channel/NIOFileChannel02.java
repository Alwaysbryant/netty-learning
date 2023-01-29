package com.netty.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 本地文件读
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/cyj/Desktop/file01.txt");
        // 读取本地文件
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate((int) (file.length()));
        channel.read(buffer);

        System.out.println(new String(buffer.array()));

        inputStream.close();

    }
}
