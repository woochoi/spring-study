package com.example.reactorpattern.reactorpatterbyme;

public interface ChannelHandler {
    void read() throws Exception;
    void write() throws Exception;
}
