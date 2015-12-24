package lexek.netty.handler.codec.engineio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

@Sharable
public class EngineIoDecoder extends MessageToMessageDecoder<WebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            EngineIoPacketType type = EngineIoPacketType.fromInt(text.charAt(0) - '0');
            String data = text.length() == 1 ? null : text.substring(1);
            out.add(new EngineIoTextPacket(type, data));
        }
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = msg.content();
            EngineIoPacketType type = EngineIoPacketType.fromInt(buf.readByte() - '0');
            ByteBuf data = buf.readableBytes() > 0 ? buf.slice().retain() : null;
            out.add(new EngineIoBinaryPacket(type, data));
        }
    }
}
