package com.example.javaio.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * Nio 에서 커널 버퍼에 직접 접근
 *
 */
@Slf4j
public class ByteBufferExample {
    public static void main(String[] args) {
        var directByteBuffer = ByteBuffer.allocateDirect(1024);
        assert directByteBuffer.isDirect();

        var heapByteBuffer = ByteBuffer.allocate(1024);
        assert !heapByteBuffer.isDirect();

        var byteBufferByWrap = ByteBuffer.wrap("hello".getBytes());
        assert !byteBufferByWrap.isDirect();
    }
}
