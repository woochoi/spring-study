package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            while (true) { // 계속 accept
                // accept > 클라이언트 소켓을 기다린다!
                Socket clientSocket = serverSocket.accept(); // 클라이언트가 연결이 되면 아래 다음 줄로 내려간다 (실행)

                byte[] requestBytes = new byte[1024];
                InputStream in = clientSocket.getInputStream();
                in.read(requestBytes); // InputStream 로 부터 값을 읽어야...

                // This is client
                log.info("request: {}", new String(requestBytes).trim());

                // 클라이언트에게 응답 값을 돌려준다
                OutputStream out = clientSocket.getOutputStream();
                String response = "This is server";
                out.write(response.getBytes());
                out.flush();
            }
        }
    }
}
