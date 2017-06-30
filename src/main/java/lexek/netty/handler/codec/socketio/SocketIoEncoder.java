package lexek.netty.handler.codec.socketio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lexek.netty.handler.codec.engineio.EngineIoPacketType;
import lexek.netty.handler.codec.engineio.EngineIoTextPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SocketIoEncoder extends MessageToMessageEncoder<SocketIoPacket> {
    private final Logger logger = LoggerFactory.getLogger(SocketIoEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, SocketIoPacket msg, List<Object> out) throws Exception {
        logger.trace("{}", msg);
        if (msg instanceof SocketIoEvent) {
            SocketIoEvent socketIoEvent = (SocketIoEvent) msg;
            if (socketIoEvent.getData() instanceof String) {
                StringBuilder data = new StringBuilder(String.valueOf(msg.getType().ordinal()));
                Long id = socketIoEvent.getId();
                if (id != null) {
                    data.append(id);
                }
                data.append(socketIoEvent.getData());
                out.add(new EngineIoTextPacket(EngineIoPacketType.MESSAGE, data.toString()));
            } else {
                throw new UnsupportedOperationException("binary events are unsupported at this moment");
            }
        }
    }
}
