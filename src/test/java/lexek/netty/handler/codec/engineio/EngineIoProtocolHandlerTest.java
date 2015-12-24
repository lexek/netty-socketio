package lexek.netty.handler.codec.engineio;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Before;

import java.util.Collections;

import static org.junit.Assert.*;

public class EngineIoProtocolHandlerTest {
    private final EngineIoProtocolHandler handler = new EngineIoProtocolHandler();
    private boolean exceptionCaught = false;

    @Before
    public void before() {
        this.exceptionCaught = false;
    }

    @org.junit.Test
    public void testClose() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(new EngineIoTextPacket(EngineIoPacketType.CLOSE, null));
        assertNull(channel.readInbound());
        assertFalse(channel.isOpen());
        channel.finish();
    }

    @org.junit.Test
    public void testPingEmpty() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(new EngineIoTextPacket(EngineIoPacketType.PING, null));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PONG, null), channel.readOutbound());
        assertNull(channel.readOutbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPingWidthTextData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(new EngineIoTextPacket(EngineIoPacketType.PING, "hello"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PONG, "hello"), channel.readOutbound());
        assertNull(channel.readOutbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPingWithBinaryData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(
                new EngineIoBinaryPacket(EngineIoPacketType.PING, Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8)));
        assertEquals(
                new EngineIoBinaryPacket(EngineIoPacketType.PONG, Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8)),
                channel.readOutbound());
        assertNull(channel.readOutbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testMessageWithoutData() throws Exception {
        ChannelHandler testHandler = new ChannelInboundHandlerAdapter() {
            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                assertTrue(cause instanceof EngineIoMalformedPacketException);
                exceptionCaught = true;
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(handler, testHandler);
        channel.writeInbound(new EngineIoBinaryPacket(EngineIoPacketType.MESSAGE, null));
        assertNull(channel.readInbound());
        assertTrue(exceptionCaught);
        channel.finish();
    }

    @org.junit.Test
    public void testMessageWithBinaryData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(
                new EngineIoBinaryPacket(EngineIoPacketType.MESSAGE, Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8)));
        assertEquals(
                new EngineIoBinaryPacket(EngineIoPacketType.MESSAGE, Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8)),
                channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testMessageWithTextData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(new EngineIoTextPacket(EngineIoPacketType.MESSAGE, "hello"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.MESSAGE, "hello"), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testNoop() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(handler);
        channel.writeInbound(new EngineIoTextPacket(EngineIoPacketType.NOOP, null));
        assertEquals(null, channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }
}