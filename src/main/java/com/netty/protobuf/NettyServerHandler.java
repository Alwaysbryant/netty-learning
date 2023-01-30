package com.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自定义handler， 需要继承netty提供的抽象类
 */
//@ChannelHandler.Sharable /** 该注解表示该处理器可以在不同的Channel中共享 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPoJo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPoJo.Student msg) throws Exception {
        System.out.println("收到的消息： id: " + msg.getId() + "; name: " + msg.getName());
    }
}
