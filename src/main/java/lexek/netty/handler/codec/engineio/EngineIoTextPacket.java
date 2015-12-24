package lexek.netty.handler.codec.engineio;

public class EngineIoTextPacket extends EngineIoPacket<String> {
    public EngineIoTextPacket(EngineIoPacketType type, String data) {
        super(type, data);
    }
}
