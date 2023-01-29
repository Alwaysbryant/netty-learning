package com.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件拷贝
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel readChannel = fileInputStream.getChannel();

//        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
//        FileChannel writeChannel = fileOutputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("3.txt");
        FileChannel writeChannel = fileOutputStream.getChannel();

//        ByteBuffer buffer = ByteBuffer.allocate(512);
//
//        while (true) {
//            // clear the buffer before reading every time
//            buffer.clear();
//            int read = readChannel.read(buffer);
//            if (read == -1) break;
//            // write data to other file
//            buffer.flip();
//            writeChannel.write(buffer);
//        }

        /**
         * 使用transformForm方法
         */
        writeChannel.transferFrom(readChannel, 0, readChannel.size());
        writeChannel.close();
        fileOutputStream.close();
        readChannel.close();
        fileInputStream.close();



    }
}
