package com.netty.rpc.publicinterface;

//公共接口，服务方和消费方都需要的
public interface HelloService {
    String hello(String msg);
}
