package lexek.netty.handler.codec.engineio;

import io.netty.handler.codec.DecoderException;

public class EngineIoMalformedPacketException extends DecoderException {
    public EngineIoMalformedPacketException() {
    }

    public EngineIoMalformedPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public EngineIoMalformedPacketException(String message) {
        super(message);
    }

    public EngineIoMalformedPacketException(Throwable cause) {
        super(cause);
    }
}
