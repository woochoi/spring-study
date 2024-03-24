package com.example.reactorpattern.reactorpatternmulti;

public interface ChannelHandler {
    void read() throws Exception;
    void write() throws Exception;
}
