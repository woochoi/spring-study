package org.example.netty.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SampleChannelInboundHandler
        extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        if (msg instanceof String) {
            // 다음 context로 이벤트를 전달하지 않고
            // outbound I/O 작업을 수행한 후 채널을 닫음
            ctx.writeAndFlush("Hello, " + msg) // channel 로 write 작업을 전달
                    .addListener(ChannelFutureListener.CLOSE);
        } else if (msg instanceof ByteBuf) {
            // 별도의 outbound I/O 작업을 수행하지 않고 다음 context로 이벤트를 전달
            // msg를 변형해서 전달 가능
            try {
                var buf = (ByteBuf) msg;
                var len = buf.readableBytes();
                var charset = StandardCharsets.UTF_8;
                var body = buf.readCharSequence(len, charset);
                ctx.fireChannelRead(body); // 다음 Context 러 read 이벤트를 전달할수있다
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }

    }
}