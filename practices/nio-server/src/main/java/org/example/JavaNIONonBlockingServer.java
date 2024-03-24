package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JavaNIONonBlockingServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false); // false : Non-Blocking

            // [main] INFO org.example.JavaNIONonBlockingServer -- request: This is client
            // 전부 main 쓰레드에서 실행됨... 개선 필요...  블로킹 발생
            while (true) {
                SocketChannel clientSocket = serverSocket.accept();
                if (clientSocket == null) { // clientSocket 값이 존재 할때 까지 (not null)
                    Thread.sleep(100);
                    //log.info("wait accept");
                    continue;
                }

                handleRequest(clientSocket); // 블로킹 발생 --> 논 블로킹하게 변경 가능
            }
        }
    }

    @SneakyThrows
    private static void handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        while (clientSocket.read(requestByteBuffer) == 0) { // 값이 존재하면 탈출
            log.info("Reading...");
        }
        requestByteBuffer.flip(); // 초기화
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request: {}", requestBody);

        Thread.sleep(10); // 블로킹

        ByteBuffer responeByteBuffer = ByteBuffer.wrap("This is server".getBytes());
        clientSocket.write(responeByteBuffer);
        clientSocket.close();
    }
}
