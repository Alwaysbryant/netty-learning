package com.netty.bound.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("客户端读取消息");
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端发送数据");
        ctx.writeAndFlush(1234567L);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcadsadewrgsvdaaf", CharsetUtil.UTF_8));
    }
}
