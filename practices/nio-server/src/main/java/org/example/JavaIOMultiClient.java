package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JavaIOMultiClient {
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    /**
     *
     * /spring-study/practices/nio-server/src/main/java/org/example/JavaNIONonBlockingMultiServer.java
     */


    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) { // 1000 번 요청, 순차적으로?
            // 하나의 결과가 끊날때 마다, 현 Core , 요청은 1000개.. .지연 생김
            var future = CompletableFuture.runAsync(() -> { // 소켓을 열고 요청을 받는 부분을 분리!
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress("localhost", 8080));

                    OutputStream out = socket.getOutputStream();
                    String requestBody = "This is client";
                    out.write(requestBody.getBytes());
                    out.flush();

                    InputStream in = socket.getInputStream();
                    byte[] responseBytes = new byte[1024];
                    in.read(responseBytes);
                    log.info("result: {}", new String(responseBytes).trim());
                } catch (Exception e) {}
            }, executorService);

            // 하나의 결과가 끊날때 마다 future 리스트 추가
            futures.add(future); // 대기
        }

        // allOf 모든 결과를 기다림
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        log.info("end main");
        long end = System.currentTimeMillis();
        log.info("duration: {}", (end - start) / 1000.0);

        // 한번 요청에 10밀리세컨드 --> duration : 12.357

    }
}
