package com.netty.reactor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 自定义handler， 需要继承netty提供的抽象类
 */
//@ChannelHandler.Sharable /** 该注解表示该处理器可以在不同的Channel中共享 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 实际读取信息
     * @param ctx 上下文对象，包含pipeline，通道
     * @param msg 客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelHandlerContext is " + ctx);
        // 将msg转换成ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("receive message, the message is " + byteBuf.toString(StandardCharsets.UTF_8));
        /*
         * 对于费时和复杂的业务操作，将消息提交到 TaskQueue
         * 对应NioEventGroupLoop
         */
        /**
         * 1. 自定义普通任务
         */
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(8000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("random...我是异步执行");
//                ctx.writeAndFlush(Unpooled.copiedBuffer("random...async execute", CharsetUtil.UTF_8));
//            }
//        });
        /**
         * 2. 定时任务
         */
        ctx.channel().eventLoop().schedule(() -> {

        }, 5, TimeUnit.SECONDS);

        System.out.println("go on ...");
    }

    /**
     * 数据读取完毕后调用处理
     * @param ctx 上下文对象
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client", StandardCharsets.UTF_8));
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
