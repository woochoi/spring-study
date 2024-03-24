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

@Slf4j
public class ServerSocketChannelSimpleExample {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        try (var serverChannel = ServerSocketChannel.open();
             var selector = Selector.open();
        ) {
            var address = new InetSocketAddress("localhost", 8080);
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);

            // ServerChannel 의 Accept 작업을 selector 에 등록
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 무한 로프를 통해서 지속적으로 채널의 작업들을 처리
            while (true) {
                selector.select(); // 준비될때까지 쓰레드 blocking, 준비가 완료된 작업들을 발견하먄 다음 Line 으로 이동

                // selector 에서 준비가 완료된 작업 목록을 가져옴
                // selectedKeys : 준비가 완료된 이벤트 목록을 Set 으로 제공
                var selectedKeys = selector.selectedKeys().iterator();

                // iterator 로 변경하여 하나씩 순회하며, 조회 이후 remove 로 제거
                // 준비가 완료된 작업들을 하나씩 처리
                while (selectedKeys.hasNext()) {
                    var key = selectedKeys.next();
                    // 준비가 완료된 작업 목록에서 제외한다
                    // iterator 가 마지막으로 반환한 값을 제거
                    selectedKeys.remove();

                    // key 안에는 channel 정보, 이벤트 정보, selector 정보 들어 있다
                    //
                    if (key.isAcceptable()) { // 작업이 ACCEPT 라면
                        // key 의 channel 에 접근/조회 하고 accept 를 실행
                        // accept 를 통해서 clientSocket 에 접근
                        var clientSocket = ((ServerSocketChannel) key.channel()).accept();
                        //--> null 체크 없다! select() 된 이후, key 가 accept 인지 체크 확인, clientSocket 를 보장

                        // clientSocket 을 non-blocking 으로 설정
                        clientSocket.configureBlocking(false);

                        // clientSocket 의 read 작업을 selector 에 등록
                        clientSocket.register(selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) { // clientSocket 에 등록했던 read 가 완료 되었다면
                        // clientSocket 에 접근
                        var clientSocket = (SocketChannel) key.channel();

                        // clientSocket 으로부터 데이터를 읽음
                        var requestBody = getRequestBody(clientSocket);

                        sendResponse(clientSocket, requestBody);
                    }
                }
            }
        }
    }

    private static String getRequestBody(SocketChannel clientSocket) throws IOException {
        // Buffer 를 만들어서 할당
        var requestBuffer = ByteBuffer.allocate(1024);
        clientSocket.read(requestBuffer);
        requestBuffer.flip();
        return new String(requestBuffer.array()).trim();
    }

    @SneakyThrows
    private static void sendResponse(SocketChannel clientSocket, String requestBody) throws IOException {
        log.info("request: {}", requestBody);
        Thread.sleep(10);
        var response = "received: " + requestBody;
        // clientSocket 에 데이터를 씀
        var responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientSocket.write(responseBuffer);
        responseBuffer.clear();

        // clientSocket 을 담음
        clientSocket.close();
    }
}
