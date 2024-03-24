package com.example.javaio.selector;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ServerSocketChannelSelectorAsyncWriteExample {
    private static ExecutorService executorService = Executors.newFixedThreadPool(30);

    @SneakyThrows
    public static void main(String[] args) throws IOException {
        log.info("start main");

        Long start = null;

        var count = 0;

        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open();
        ) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            // serverChannel 을 non-blocking 으로 설정
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // 준비될때까지 blocking
                if (start == null) start = System.currentTimeMillis();

                var selectedKeys = selector.selectedKeys();
                for (var key : selectedKeys) {
                    if (!key.isValid()) return;

                    if (key.isAcceptable()) { // event 가 ACCEPT 라면
                        // accept 를 통해서 clientSocket 에 접근
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();

                        // clientSocket 을 non-blocking 으로 설정
                        clientSocket.configureBlocking(false);

                        // ServerChannel 의 Accept 작업을 selector 에 등록
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) { // event 가 READ 라면
                        var clientSocket = (SocketChannel) key.channel();
                        var requestBody = getRequestBody(clientSocket);

                        // clientSocket 에 데이터를 씀
                        sendResponse(clientSocket, requestBody);
                        count++;
                    }
                }
                if (count == 1000) break;
                selectedKeys.clear();
            }
        }

        var duration = System.currentTimeMillis() - start;
        log.info("duration: {}", duration);
        executorService.shutdown();
    }

    private static String getRequestBody(SocketChannel clientSocket) throws IOException {
        var requestBuffer = ByteBuffer.allocate(1024);
        clientSocket.read(requestBuffer);
        requestBuffer.flip();
        return new String(requestBuffer.array()).trim();
    }

    @SneakyThrows
    private static void sendResponse(SocketChannel clientSocket, String requestBody) throws IOException {
        try {
            log.info("request: {}", requestBody);
            Thread.sleep(10);
            var response = "received: " + requestBody;
            var responseBuffer = ByteBuffer.wrap(response.getBytes());
            clientSocket.write(responseBuffer);
            responseBuffer.clear();
            clientSocket.close();
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
