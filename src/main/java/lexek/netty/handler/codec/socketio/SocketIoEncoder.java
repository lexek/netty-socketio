package lexek.netty.handler.codec.socketio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lexek.netty.handler.codec.engineio.EngineIoPacketType;
import lexek.netty.handler.codec.engineio.EngineIoTextPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SocketIoEncoder extends MessageToMessageEncoder<SocketIoPacket> {
    Logger logger = LoggerFactory.getLogger(SocketIoEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, SocketIoPacket msg, List<Object> out) throws Exception {
        logger.trace("{}", msg);
        if (msg instanceof SocketIoEvent) {
            if (((SocketIoEvent) msg).getData() instanceof String) {
                String data = String.valueOf(msg.getType().ordinal()) +
                    ((SocketIoEvent) msg).getId() +
                    ((SocketIoEvent) msg).getData();
                out.add(new EngineIoTextPacket(EngineIoPacketType.MESSAGE, data));
            } else {
                throw new UnsupportedOperationException("binary events are unsupported at this moment");
            }
        }
    }
}
