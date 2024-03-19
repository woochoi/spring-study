package com.example.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

@Slf4j
public class AsyncServerSocketFutureExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var serverSocketChannel = AsynchronousServerSocketChannel.open();
        var address = new InetSocketAddress("localhost", 8080);
        serverSocketChannel.bind(address);

        // accept 가 Future 를 반환
        Future<AsynchronousSocketChannel> clientSocketFuture = serverSocketChannel.accept();
        while (!clientSocketFuture.isDone()) {
            Thread.sleep(100);
            log.info("Waiting...");
        }
        var clientSocket = clientSocketFuture.get(); // clientSocket 에 접근이 가능하다

        var requestBuffer = ByteBuffer.allocateDirect(1024); // 버퍼 준비
        Future<Integer> channelRead = clientSocket.read(requestBuffer); // Future 를 반환
        while (!channelRead.isDone()) {
            log.info("Reading...");
        }

        requestBuffer.flip(); // 읽을 수 있다
        var request = StandardCharsets.UTF_8.decode(requestBuffer);
        log.info("request: {}", request);

        var response = "This is server.";
        var responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientSocket.write(responseBuffer);
        clientSocket.close();
        log.info("end client");
}
}
