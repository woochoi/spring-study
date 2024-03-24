package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JavaNIONonBlockingMultiServer {

    /**
     * /spring-study/practices/nio-server/src/main/java/org/example/JavaIOMultiClient.java
     */

    // 한번 요청을 처리할때 마다 int c = count.incrementAndGet(); 통해서 하나씩 늘림
    private static AtomicInteger count = new AtomicInteger(0);
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);


            // Java NIO non-blocking 의 문제점
            while (true) { // 동시에 발생하는 요청이 증가하는 경우, 연결 처리가 순차적으로 발생하여 성능 감소
                SocketChannel clientSocket = serverSocket.accept(); // main 쓰레드에서 accept 완료를 주기적으로 확인
                if (clientSocket == null) { // 수동으로 채널 상태 체크.... ㅜㅜ 개선 필요 --> part 8 에서 살펴본다!
                    Thread.sleep(100);
                    continue;
                }

                // 별도 쓰레드에서 실행...
                CompletableFuture.runAsync(() -> {
                    handleRequest(clientSocket);
                });

                /** 조금 빨라짐, 무조건 Thread 가 늘어난다고 빨라지진 않아
                CompletableFuture.runAsync(() -> {
                    handleRequest(clientSocket);
                }, executorService);
                 */
            }
        }
    }

    @SneakyThrows
    private static void handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        while (clientSocket.read(requestByteBuffer) == 0) {
            log.info("Reading...");
        }
        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request: {}", requestBody);

        Thread.sleep(10);

        ByteBuffer responeByteBuffer = ByteBuffer.wrap("This is server".getBytes());
        clientSocket.write(responeByteBuffer);
        clientSocket.close();
        int c = count.incrementAndGet();
        log.info("count: {}", c);
    }
}
