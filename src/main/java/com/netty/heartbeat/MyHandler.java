package com.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(evt.getClass());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();
            switch (state) {
                case ALL_IDLE:
                    System.out.println("全部空闲");
                case READER_IDLE:
                    System.out.println("读空闲");
                case WRITER_IDLE:
                    System.out.println("写空闲");
            }
        }
    }
}
