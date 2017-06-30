package lexek.netty.handler.codec.engineio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public class EngineIoBinaryPacket extends EngineIoPacket<ByteBuf> implements ByteBufHolder {
    public EngineIoBinaryPacket(EngineIoPacketType type, ByteBuf data) {
        super(type, data);
    }

    @Override
    public ByteBuf content() {
        return getData();
    }

    @Override
    public ByteBufHolder copy() {
        return new EngineIoBinaryPacket(getType(), getData().copy());
    }

    @Override
    public ByteBufHolder duplicate() {
        return new EngineIoBinaryPacket(getType(), getData().duplicate());
    }

    @Override
    public int refCnt() {
        return getData().refCnt();
    }

    @Override
    public ByteBufHolder retain() {
        if (getData() != null) {
            getData().retain();
        }
        return this;
    }

    @Override
    public ByteBufHolder retain(int increment) {
        if (getData() != null) {
            getData().retain(increment);
        }
        return this;
    }

    @Override
    public ByteBufHolder touch() {
        return null;
    }

    @Override
    public ByteBufHolder touch(Object o) {
        return null;
    }

    @Override
    public boolean release() {
        return getData() != null && getData().release();
    }

    @Override
    public boolean release(int decrement) {
        return getData().release(decrement);
    }
}
