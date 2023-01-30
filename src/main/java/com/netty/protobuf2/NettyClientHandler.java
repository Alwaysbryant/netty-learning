package com.netty.protobuf2;

import com.netty.protobuf.StudentPoJo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    /**
     * 发生读事件时触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("消息： " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client handler ctx: " + ctx);
        int random = new Random().nextInt(3);
        PoJo.MyMessage myMessage = null;
        if (random == 0) {
            myMessage = PoJo.MyMessage.newBuilder()
                    .setDataType(PoJo.MyMessage.DataType.studentType)
                    .setStudent(PoJo.Student.newBuilder().setId(58).setName("Tom").build()).build();
        } else {
            myMessage = PoJo.MyMessage.newBuilder()
                    .setDataType(PoJo.MyMessage.DataType.workerType)
                    .setWorker(PoJo.Worker.newBuilder().setAge(30).setName("John").build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }
}
