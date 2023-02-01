package com.netty.protocoltcp;

import com.netty.protocoltcp.message.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println(msg.getLen() + " " + new String(msg.getContent(), CharsetUtil.UTF_8));
        // 回复
        String response = UUID.randomUUID().toString();
        int len = response.length();
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        ctx.writeAndFlush(new MessageProtocol(len, bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
