package com.netty.nio.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class ByteBufTest02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello, netty", CharsetUtil.UTF_8);
        int i = byteBuf.readableBytes();

    }
}
