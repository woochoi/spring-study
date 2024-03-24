package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class JavaIOClient {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (Socket socket = new Socket()) {
            // 서버가 먼저 실행 되어야 된다 > 서버 응답 대기
            socket.connect(new InetSocketAddress("localhost", 8080));

            OutputStream out = socket.getOutputStream();
            String requestBody = "This is client"; // 서버에서 응답을 처리하고 돌려줌
            out.write(requestBody.getBytes());
            out.flush();

            // 응답 대기
            InputStream in = socket.getInputStream();
            byte[] responseBytes = new byte[1024];
            in.read(responseBytes); // read > 읽기 위해 responseBytes 에 저장한다

            // This is server
            log.info("result: {}", new String(responseBytes).trim());
        }
        log.info("end main");
    }
}
