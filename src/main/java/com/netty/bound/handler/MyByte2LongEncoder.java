package com.netty.bound.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyByte2LongEncoder
        extends MessageToByteEncoder<Long>
/** <I> 编码器是否生效会根据当前泛型决定，
 * {@link MessageToByteEncoder#acceptOutboundMessage(java.lang.Object)}
 * 如果指定了对应的泛型， 写入的数据类型与之不同， 则不会生肖
 * eg： 写入数据为  String s = "Hello, Netty", 此处指定的为： MessageToByteEncoder<Long> 则不会执行该处理器*/
{

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("客户端收到的消息： " + msg);
        out.writeLong( msg);
    }
}
