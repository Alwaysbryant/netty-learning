package com.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;


public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        // Test CPU cores
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
        // 创建BossGroup和workGroup
        // BossGroup只处理连接请求， workerGroup处理read/write
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 设置启动参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    // 使用NioSocketChannel作为服务器的通道
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列的最大连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    // 给对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入protobuf的解码器, 需要指定对哪种对象进行解码
                            pipeline.addLast(new ProtobufDecoder(StudentPoJo.Student.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("the server is ready...");
            // 绑定端口，同步数据
            ChannelFuture channelFuture = bootstrap.bind(18111).addListener(listen -> {
                if (listen.isSuccess()) {
                    System.out.println("端口绑定成功");
                } else {
                    System.out.println("端口绑定失败");
                }
             }).sync();

            // 监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
