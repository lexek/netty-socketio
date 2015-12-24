package lexek.netty.handler.codec.engineio;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCounted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.TimeUnit;

@Sharable
public class EngineIoProtocolHandler extends SimpleChannelInboundHandler<EngineIoPacket> {
    private final Logger logger = LoggerFactory.getLogger(EngineIoProtocolHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EngineIoPacket msg) throws Exception {
        switch (msg.getType()) {
            case OPEN:
                if (msg.getData() == null) {
                    throw new EngineIoMalformedPacketException("OPEN packet should have data");
                }
                if (msg instanceof EngineIoTextPacket) {
                    JsonNode handshakeData = objectMapper.readTree((String) msg.getData());
                    long pingTimeout = handshakeData.get("pingTimeout").asLong();
                    long pingInterval = handshakeData.asLong(pingTimeout / 2);
                    ctx
                        .pipeline()
                        .addBefore(ctx.name(), "idleHandler", new IdleStateHandler(pingTimeout, 0, 0, TimeUnit.MILLISECONDS));
                    new EngineIoPinger(pingInterval, ctx.channel()).run();
                } else {
                    throw new EngineIoMalformedPacketException("OPEN packet shouldn't be binary");
                }
                break;
            case CLOSE:
                logger.debug("received close packet, closing connection now");
                ctx.close();
                break;
            case PING:
                logger.debug("received ping packet, sending back pong packet now");
                if (msg instanceof EngineIoBinaryPacket) {
                    ctx.writeAndFlush(new EngineIoBinaryPacket(EngineIoPacketType.PONG,
                        ((ByteBuf) msg.getData()).duplicate().retain()));
                }
                if (msg instanceof EngineIoTextPacket) {
                    ctx.writeAndFlush(new EngineIoTextPacket(EngineIoPacketType.PONG, (String) msg.getData()));
                }
                break;
            case PONG:
                logger.trace("received pong");
                //do nothing i guess
                break;
            case UPGRADE:
                //TODO:??
                throw new NotImplementedException();
            case NOOP:
                ctx.flush();
                break;
            case MESSAGE:
                if (msg.getData() == null) {
                    throw new EngineIoMalformedPacketException("MESSAGE packet should have data");
                }
                if (msg instanceof ReferenceCounted) {
                    ((ReferenceCounted) msg).retain();
                }
                logger.debug("passing {} packet further", msg.getType());
                ctx.fireChannelRead(msg);
                break;
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == IdleState.READER_IDLE) {
            ctx.close();
        }
    }
}
