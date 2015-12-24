package lexek.netty.handler.codec.socketio;

public class SocketIoError extends SocketIoPacket {
    private final String data;

    public SocketIoError(String namespace, String data) {
        super(SocketIoPacketType.ERROR, namespace);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
