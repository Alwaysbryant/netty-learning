package com.netty.protocoltcp;

import com.netty.protocoltcp.message.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    /**
     * mock 粘包
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String content = "Java是世界上最好的语言.cpp";
        for (int i = 0; i < 5; i++) {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            int len = bytes.length;
            // 创建协议包
            MessageProtocol messageProtocol = new MessageProtocol(len, bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("client response...");
        System.out.println("client response...." + msg.getLen() + " " + new String(msg.getContent(), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
