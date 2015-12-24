package lexek.netty.handler.codec.socketio;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketIoClientHandler extends SimpleChannelInboundHandler<SocketIoEvent> {
    public SocketIoClientHandler(ObjectMapper objectMapper) {

    }

    public SocketIoClientHandler() {
        this(new ObjectMapper());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SocketIoEvent msg) throws Exception {

    }
}
