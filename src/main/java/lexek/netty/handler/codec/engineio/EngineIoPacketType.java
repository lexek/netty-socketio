package lexek.netty.handler.codec.engineio;

public enum EngineIoPacketType {
    OPEN,
    CLOSE,
    PING,
    PONG,
    MESSAGE,
    UPGRADE,
    NOOP;

    private static final EngineIoPacketType[] typeArray = EngineIoPacketType.values();

    static EngineIoPacketType fromInt(int x) {
        return typeArray[x];
    }
}
