package lexek.netty.handler.codec.engineio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Sharable
public class EngineIoEncoder extends MessageToMessageEncoder<EngineIoPacket> {
    Logger logger = LoggerFactory.getLogger(EngineIoEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, EngineIoPacket msg, List<Object> out) throws Exception {
        logger.trace("{}", msg);
        if (msg instanceof EngineIoTextPacket) {
            StringBuilder result = new StringBuilder();
            result.append(msg.getType().ordinal());
            if (msg.getData() != null) {
                result.append(((EngineIoTextPacket) msg).getData());
            }
            out.add(new TextWebSocketFrame(result.toString()));
        }
        if (msg instanceof EngineIoBinaryPacket) {
            ByteBuf buf = ctx.alloc().buffer();
            buf.writeInt(msg.getType().ordinal() + '0');
            buf.writeBytes(((EngineIoBinaryPacket) msg).getData());
            out.add(new BinaryWebSocketFrame(buf));
        }
    }
}
