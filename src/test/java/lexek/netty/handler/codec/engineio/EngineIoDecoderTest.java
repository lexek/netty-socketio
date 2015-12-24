package lexek.netty.handler.codec.engineio;


import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EngineIoDecoderTest {
    private final EngineIoDecoder decoder = new EngineIoDecoder();

    @org.junit.Test
    public void testOpen() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("0"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.OPEN, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testOpenBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("0", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.OPEN, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testClose() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("1"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.CLOSE, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testCloseBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("1", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.CLOSE, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPing() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("2"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PING, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPingBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("2", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.PING, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPingWithData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("2kek"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PING, "kek"), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPingWithDataBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("2kek", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.PING, Unpooled.copiedBuffer("kek", CharsetUtil.UTF_8)),
                channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPong() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("3"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PONG, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPongBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("4", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.MESSAGE, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPongWithData() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("3kek"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.PONG, "kek"), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testPongWithDataBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("3kek", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.PONG, Unpooled.copiedBuffer("kek", CharsetUtil.UTF_8)),
                channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testMessage() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("4kek"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.MESSAGE, "kek"), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testMessageBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("4kek", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.MESSAGE, Unpooled.copiedBuffer("kek", CharsetUtil.UTF_8)),
                channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testUpgrade() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("5"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.UPGRADE, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testUpgradeBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("5", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.UPGRADE, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testNoop() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new TextWebSocketFrame("6"));
        assertEquals(new EngineIoTextPacket(EngineIoPacketType.NOOP, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }

    @org.junit.Test
    public void testNoopBinary() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(decoder);
        channel.writeInbound(new BinaryWebSocketFrame(Unpooled.copiedBuffer("6", CharsetUtil.UTF_8)));
        assertEquals(new EngineIoBinaryPacket(EngineIoPacketType.NOOP, null), channel.readInbound());
        assertNull(channel.readInbound());
        channel.finish();
    }
}