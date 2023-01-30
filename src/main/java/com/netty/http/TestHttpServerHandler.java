package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
//    /**
//     * 读取
//     * @param ctx
//     * @param msg
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        /*
        * 判断msg是否为httpRequest请求
         */
        if (! (msg instanceof HttpRequest)) {
            System.out.println("不是http请求");
            return;
        }
        /*
        * 通过uri可以过滤特定的资源
         */
        HttpRequest request = (HttpRequest) msg;
        URI uri = new URI(request.getUri());
        if (uri.getPath().contains("favicon.ico")) {
            System.out.println("不做处理...");
            return;
        }
        System.out.println("收到的消息: " + msg.toString());
        /*
        * 回复浏览器
        * 构造http响应
         */
        ByteBuf content = Unpooled.copiedBuffer("fuck you, bitch", CharsetUtil.UTF_8);
        FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        res.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        res.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        ctx.writeAndFlush(res);
    }

//    /**
//     * 读取完成
//     * @param ctx
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.copiedBuffer("fuck you, bitch", CharsetUtil.UTF_8));
//    }

    /**
     * 异常捕获
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("操作失败，原因： " + cause.toString());
    }
}
