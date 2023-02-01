package com.netty.bound.initializer;

import com.netty.bound.handler.MyByte2LongDecoder;
import com.netty.bound.handler.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 服务端读取数据， inbound
 * ChannelPipeline 维护的是一个双向链表
 * 所以 出站的时候， 处理器的调用链， 从 head ===>>>> 往后
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByte2LongDecoder());
        pipeline.addLast(new MyByte2LongDecoder());
        pipeline.addLast("myServerHandler", new MyServerHandler());
    }
}
