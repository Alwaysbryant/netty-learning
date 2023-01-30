package com.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 基于http协议的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块方式进行数据的写入
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 对于大数据，会拆分进行传输，这里聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /*
                            * websocket ws协议，是C/S握手建立的连接，长连接，
                            * 同http协议一样，应用层协议，基于TCP
                            * 使用netty提供的handler，将http协议升级/转换为 ws协议
                            *
                            * 101状态码升级协议
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            /*
                             * 自定义，处理请求
                             */
                            pipeline.addLast(new MyWebSocketHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(18111).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
