package lexek.netty.handler.codec.socketio;

import java.util.Objects;

public class SocketIoPacket {
    private final SocketIoPacketType type;
    private final String namespace;

    public SocketIoPacket(SocketIoPacketType type, String namespace) {
        this.type = type;
        this.namespace = namespace;
    }

    public SocketIoPacketType getType() {
        return type;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketIoPacket that = (SocketIoPacket) o;
        return Objects.equals(type, that.type) &&
            Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, namespace);
    }

    @Override
    public String toString() {
        return "SocketIoPacket{" +
            "type=" + type +
            ", namespace='" + namespace + '\'' +
            '}';
    }
}
