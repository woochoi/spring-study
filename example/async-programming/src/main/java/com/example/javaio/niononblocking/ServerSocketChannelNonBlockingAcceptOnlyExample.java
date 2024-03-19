package com.example.javaio.niononblocking;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * JAva NIO 를 non-blocking 하게 사용하려면
 * SelectableChannel > AbstractSelectableChannel . configureBlocking
 */
@Slf4j
public class ServerSocketChannelNonBlockingAcceptOnlyExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");
        try (var serverChannel = ServerSocketChannel.open()) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false); // false : non-blocking

            // non-blocking accept 가 된다!
            var clientSocket = serverChannel.accept(); // 기대리지 않고 다음 줄로 넘어감
            assert clientSocket == null;
        }
        log.info("end main");
    }
}
