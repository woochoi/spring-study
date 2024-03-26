package org.example.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopIOTaskExample {
    public static void main(String[] args) {
        log.info("start main");

        // Netty 전용 ServerSocketChannel
        var channel = new NioServerSocketChannel();

        // NIOEventLoop 은 직접 생성할수 없기 때문에 NIOEventLoopGroup 을 사용
        // 하나의 NIOEventLoopGroup 에 여러개의 channel 등록 가능 (하나의 EventLoop 에 accept 를 여러개 받을 수 있다!)
        var eventLoopGroup = new NioEventLoopGroup(1); // EventLoop 가 하나이다!

        // channel 을 생성하고 accept network I/O 이벤트를 EventLoop 에 등록
        eventLoopGroup.register(channel);

        channel.bind(new InetSocketAddress(8080))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("Server bound to port 8080");
                    } else {
                        log.info("Failed to bind to port 8080");
                        eventLoopGroup.shutdownGracefully();
                    }
                });
        log.info("end main");
    }
}