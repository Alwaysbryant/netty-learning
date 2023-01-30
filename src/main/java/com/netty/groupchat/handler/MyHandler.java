package com.netty.groupchat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyHandler extends SimpleChannelInboundHandler<String> {
    /**  管理所有的channel */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**  定义时间格式      */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 在进行处理器处理时，将当前的channel加入到集合中，并发送加入聊天通知
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[" + sdf.format(new Date()) + "] " + "用户[" + channel.remoteAddress() +  "]加入了聊天\n");
        channels.add(channel);
    }

    /**
     * 断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[" + sdf.format(new Date()) + "] " + "用户[" + channel.remoteAddress() +  "]离开了了聊天\n");
    }

    /**
     * channel处于活动状态， 用来发送上线通知
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * channel处理非活动状态，提示下线
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[" + sdf.format(new Date()) + "] " + "用户[" + channel.remoteAddress() +  "]下线了\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 读取&转发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channels.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[" + sdf.format(new Date()) + "] " + "用户[" + channel.remoteAddress() +  "]: " + msg + "\n");
            }
        });
    }
}
