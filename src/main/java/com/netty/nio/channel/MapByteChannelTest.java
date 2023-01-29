package com.netty.nio.channel;



import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MapByteChannel可以直接在堆外内存中修改文件
 */
public class MapByteChannelTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = rw.getChannel();

        /*
         * 指定类型
         * 修改文件的起始位置
         * 修改文件的结束位置
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 6);
        map.put(0, (byte) 'H');
        map.put(3, (byte)'F');

        channel.close();

        rw.close();
    }
}
