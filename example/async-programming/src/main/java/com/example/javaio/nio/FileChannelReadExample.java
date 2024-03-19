package com.example.javaio.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileChannelReadExample {
    public static void main(String[] args) throws IOException {
        log.info("start main");
        // 불러오기
        var file = new File(FileChannelReadExample.class
                .getClassLoader()
                .getResource("hello.txt")
                .getFile());

        try (var fileChannel = FileChannel.open(file.toPath())) { // fileChannel 을 열었다
            var byteBuffer = ByteBuffer.allocateDirect(1024);
            fileChannel.read(byteBuffer);
            byteBuffer.flip(); // positon 을 0, 다시 읽어야 (positon ~ limit)

            var result = StandardCharsets.UTF_8.decode(byteBuffer);
            log.info("result: {}", result);
        }
        log.info("end main");
    }
}
