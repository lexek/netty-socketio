package lexek.netty.handler.codec.socketio;

public enum SocketIoPacketType {
    CONNECT,
    DISCONNECT,
    EVENT,
    ACK,
    ERROR,
    BINARY_EVENT,
    BINARY_ACK;

    private static final SocketIoPacketType[] typeArray = SocketIoPacketType.values();

    static SocketIoPacketType fromInt(int x) {
        return typeArray[x];
    }
}
