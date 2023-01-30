package com.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // HttpServerCodec == >> 处理http 编解码器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());
        // 自定义处理器
        pipeline.addLast("myHttpServerHandler", new TestHttpServerHandler());
    }
}
