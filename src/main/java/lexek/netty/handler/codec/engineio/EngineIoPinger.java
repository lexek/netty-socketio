package lexek.netty.handler.codec.engineio;

import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;

public class EngineIoPinger implements Runnable {
    private final long interval;
    private final Channel channel;

    public EngineIoPinger(long interval, Channel channel) {
        this.interval = interval;
        this.channel = channel;
    }

    @Override
    public void run() {
        if (channel.isActive()) {
            channel.writeAndFlush(new EngineIoTextPacket(EngineIoPacketType.PING, null));
            channel.eventLoop().schedule(this, interval, TimeUnit.MILLISECONDS);
        }
    }
}
