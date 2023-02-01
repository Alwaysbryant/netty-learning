package com.netty.protocoltcp.message;

import java.util.Arrays;

public class MessageProtocol {
    /**
     * 消息长度
     */
    private int len;
    /**
     * 消息内容
     */
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MessageProtocol(int len, byte[] content) {
        this.len = len;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "len=" + len +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
