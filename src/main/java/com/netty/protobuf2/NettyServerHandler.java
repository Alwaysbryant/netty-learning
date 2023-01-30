package com.netty.protobuf2;

import com.netty.protobuf.StudentPoJo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自定义handler， 需要继承netty提供的抽象类
 */
//@ChannelHandler.Sharable /** 该注解表示该处理器可以在不同的Channel中共享 */
public class NettyServerHandler extends SimpleChannelInboundHandler<PoJo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PoJo.MyMessage msg) throws Exception {
        PoJo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == PoJo.MyMessage.DataType.studentType) {
            PoJo.Student student = msg.getStudent();
            System.out.println("student>>> id=" + student.getId() + ".name=" + student.getName());
        } else if (dataType == PoJo.MyMessage.DataType.workerType) {
            PoJo.Worker worker = msg.getWorker();
            System.out.println("worker>>> age=" + worker.getAge() + ".name=" + worker.getName());
        } else {
            System.out.println("类型错误");
        }
    }
}
