package lexek.netty.handler.codec.engineio;

import java.util.Objects;

public abstract class EngineIoPacket<T> {
    private final EngineIoPacketType type;
    private final T data;

    public EngineIoPacket(EngineIoPacketType type, T data) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
        this.data = data;
    }

    public EngineIoPacketType getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EngineIoPacket that = (EngineIoPacket) o;
        return Objects.equals(type, that.type) &&
            Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }

    @Override
    public String toString() {
        return "EngineIoPacket{" +
            "type=" + type +
            ", data='" + data + '\'' +
            '}';
    }
}
