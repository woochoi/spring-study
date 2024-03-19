package com.example.javaio.aio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AsyncServerSocketCallbackExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var serverSocketChannel = AsynchronousServerSocketChannel.open();
        var address = new InetSocketAddress("localhost", 8080);
        serverSocketChannel.bind(address);

        serverSocketChannel.accept(null, new CompletionHandler<>() {

            // completed 가 됬다는건 accept 가 끝났다는 의미
            @Override   // callback 에게 clientSocket 를 전달, callback 에서 받는다!
            public void completed(AsynchronousSocketChannel clientSocket, Object attachment) {
                log.info("accepted");
                var requestBuffer = ByteBuffer.allocateDirect(1024); // 버퍼를 준비

                clientSocket.read(requestBuffer, null, new CompletionHandler<>() { // callback 을 넘긴다! callback 안에 callback
                    @SneakyThrows
                    @Override
                    public void completed(Integer a, Object attachment) {
                        requestBuffer.flip();
                        var request = StandardCharsets.UTF_8.decode(requestBuffer);
                        log.info("request: {}", request);

                        var response = "This is server.";
                        var responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientSocket.write(responseBuffer);
                        clientSocket.close();
                        log.info("end client");
                    }

                    @Override
                    public void failed(Throwable ex, Object attachment) { /* do nothing */ }
                });
            }

            @Override
            public void failed(Throwable ex, Object attachment) { /* do nothing */ }
        });

        Thread.sleep(100_000);
        log.info("end main");
}
}
