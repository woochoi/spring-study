package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SelectorServer {
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open(); // Selector 를 얻는다!
        ) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT); // 관심있는 이벤트 등록
            //--> serverSocket 에서 OP_ACCEPT 가 준비되면 쓰레드에게 알려 준다
            //--> 이제 serverSocket 은 ACCEPT 할 준비가 됬어

            while (true) {
                selector.select(); // 준비될때까지 blocking, busy-wait 제거
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next(); // 키를 가져온다!
                    selectedKeys.remove(); // 중복 이벤트 처리 방지... 키를 가져오고 반드시 바로 제거

                    // key 안에는 어떤 채널, 어떤 Selector, 어떤 이벤트인지 정보가 들어있다!
                    if (key.isAcceptable()) {

                        // null 이 될 수 없다!
                        SocketChannel clientSocket = ((ServerSocketChannel)key.channel()).accept();

                        clientSocket.configureBlocking(false); // non-blocking

                        // clientSocket 은 먼저 값을 읽어서(read) 서버에게 write 을 해야되기 때문
                        // clientSocket 에 selector 를 등록하면서 read 이벤트를 등록
                        clientSocket.register(selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) { // read 가 준비 됬다! 무조건 값이 있다고 보장!

                        SocketChannel clientSocket = (SocketChannel) key.channel(); // 접근

                        String requestBody = handleRequest(clientSocket);

                        sendResponse(clientSocket, requestBody); // clientSocket 에 전달
                    }
                }
            }
        }
    }

    @SneakyThrows
    private static String handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        clientSocket.read(requestByteBuffer);

        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request: {}", requestBody);

        return requestBody;
    }

    @SneakyThrows
    private static void sendResponse(SocketChannel clientSocket, String requestBody) {
        Thread.sleep(10); // or 100, 대기 블로킹 가정... 개선 필요! --> SelectorMultiServer.java
        /**
         * CompletableFuture.runAsync(() -> {
         *             try {
         *                 Thread.sleep(10);
         *
         *                 String content = "received: " + requestBody;
         *                 ByteBuffer responeByteBuffer = ByteBuffer.wrap(content.getBytes());
         *                 clientSocket.write(responeByteBuffer);
         *                 clientSocket.close();
         *             } catch (Exception e) { }
         *         }, executorService);
         */


        // 요청으로 받을 값을 활용해서 clientSocket 에 응답을 준다!
        String content = "received: " + requestBody;
        ByteBuffer responeByteBuffer = ByteBuffer.wrap(content.getBytes());
        clientSocket.write(responeByteBuffer);
        clientSocket.close();
    }
}
