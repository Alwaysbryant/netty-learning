### NIO
Non-blocking I/O 非阻塞IO
Channel： 通信的管道
Selector：选择器
服务端开启一个channel并注册到selector上，selector监听建立请求的事件，并获取到所有与服务端的channel建立连接的客户端，并将所有客户端的channel注册到selector上，有selector来进行I/O操作。
```java
/**
 * 服务端
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 服务端通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 创建一个选择器
        Selector selector = Selector.open();

        // 监听端口
        channel.socket().bind(new InetSocketAddress(18130));
        // 设置为非阻塞
        channel.configureBlocking(Boolean.FALSE);
        // 将服务端的channel注册到选择器中
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒无连接");
                continue;
            }
            // 获取所有注册的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            if (selectionKeys == null || selectionKeys.size() == 0) return;

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // selector 监听事件，如果当前的selectionKey是可接收新连接的，注册key对应的channel
                if (key.isAcceptable()) {
                    // 接收数据
                    SocketChannel socketChannel = channel.accept();
                    // 指定为非阻塞
                    socketChannel.configureBlocking(Boolean.FALSE);
                    // 注册channel
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                // 根据key从selector中获取到channel，并从中读取数据
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer attachment = (ByteBuffer) key.attachment();
                    socketChannel.read(attachment);
                    System.out.println("读取到客户端的数据" + new String(attachment.array()));
                }

                iterator.remove();
            }
        }


    }
}
```
### Reactor单线程模式
reactor负责建立连接，并且需要分配对应的handler来处理I/O
### Reactor主从模式
主线程只负责与客户端建立连接，实际I/O交给子线程来处理，子线程创建连接队列进行监听，并创建handler进行事件处理。
当有事件发生时，则选择对应的handler来对事件进行处理。
### Netty
Netty的设计模式就是基于Reactor主从模式，基于事件驱动来处理。bossGroup线程组专门处理连接，workerGroup线程组来处理具体的业务以及I/O操作。
PS： **Essential Netty in Action**： https://waylau.com/essential-netty-in-action/index.html
### handler
ChannelInboundHandler：处理入站事件
ChannelOutboundHandler：处理出站事件
ChannelDuplexHandler： 都可以处理

适配器模式
```java
/**
** ChannelInboundHandlerAdapter 被适配类， 处理入站事件
** ChannelOutboundHandler       目标对象， 处理出站事件
*/ 
public class ChannelDuplexHandler extends ChannelInboundHandlerAdapter implements ChannelOutboundHandler {
  
}
```

