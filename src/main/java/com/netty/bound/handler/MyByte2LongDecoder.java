package com.netty.bound.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 */
public class MyByte2LongDecoder extends ByteToMessageDecoder {
    private final static int LONG_BYTES = 8;

    /**
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("开始解码");
        if (in.readableBytes() >= LONG_BYTES) {
            out.add(in.readLong());
        }
    }
}
