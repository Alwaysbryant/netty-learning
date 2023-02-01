package com.netty.bound.initializer;

import com.netty.bound.handler.MyByte2LongDecoder;
import com.netty.bound.handler.MyByte2LongEncoder;
import com.netty.bound.handler.MyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * 客户端发出数据， outbound
 * ChannelPipeline 维护的是一个双向链表
 * 所以 出站的时候， 处理器的调用链， 从 tail ===>>>> 往前
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByte2LongEncoder());
        pipeline.addLast(new MyByte2LongDecoder());
        pipeline.addLast("myClientHandler", new MyClientHandler());
    }
}
