package com.example.javaio.io;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class ServerSocketInputStreamExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start maiin");
        // 서버 소켓 생성, 8080 을 Listen 하는 서버...
        ServerSocket serverSocket = new ServerSocket(8080);

        // 클라이언트 접속 대기, 블로킹...
        Socket clientSocket = serverSocket.accept();

        var inputStream = clientSocket.getInputStream();

        // 클라이언트부터 데이터를 읽어들임
        var bis = new BufferedInputStream(inputStream);

        // 1KB 크기의 버퍼를 생성
        byte[] buffer = new byte[1024];
        int bytesRead = bis.read(buffer); // 한번에 읽어드림
        String inputLine = new String(buffer, 0, bytesRead);
        log.info("bytes: {}", inputLine);

        clientSocket.close(); // 클라이언트 소켓 닫기
        serverSocket.close(); // 서버 소켓 닫기
        log.info("end main");
    }
}
