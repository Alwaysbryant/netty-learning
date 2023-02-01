package com.netty.protocoltcp.codec;

import com.netty.protocoltcp.message.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码开始");
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        out.add(new MessageProtocol(len, content));

    }
}
