package lexek.netty.handler.codec.socketio;

import java.util.Objects;

public class SocketIoEvent<T> extends SocketIoPacket {
    private final Long id;
    private final T data;

    public SocketIoEvent(SocketIoPacketType type, String namespace, Long id, T data) {
        super(type, namespace);
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SocketIoEvent<?> that = (SocketIoEvent<?>) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, data);
    }

    @Override
    public String toString() {
        return "SocketIoEvent{" +
            "id=" + id +
            ", data=" + data +
            "} " + super.toString();
    }
}
