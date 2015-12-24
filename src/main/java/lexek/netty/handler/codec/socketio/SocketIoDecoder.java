package lexek.netty.handler.codec.socketio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lexek.netty.handler.codec.engineio.EngineIoPacket;
import lexek.netty.handler.codec.engineio.EngineIoPacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class SocketIoDecoder extends MessageToMessageDecoder<EngineIoPacket> {
    private final Logger logger = LoggerFactory.getLogger(SocketIoDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, EngineIoPacket msg, List<Object> out) throws Exception {
        if (msg.getType() == EngineIoPacketType.MESSAGE) {
            if (msg.getData() == null) {
                logger.debug("MESSAGE packet has no data");
                return;
            }
            String s = (String) msg.getData();
            SocketIoPacketType type = SocketIoPacketType.fromInt(s.charAt(0) - '0');
            String data = s.length() == 1 ? "" : s.substring(1);
            int i = 0;
            String namespace = "/";
            if (!data.isEmpty()) {
                if (data.charAt(i) == '/') {
                    StringBuilder sb = new StringBuilder();
                    for (; i < data.length(); i++) {
                        char c = data.charAt(i);
                        if (c != ',') {
                            sb.append(c);
                        } else {
                            break;
                        }
                    }
                    namespace = sb.toString();
                }
            }
            switch (type) {
                case CONNECT:
                    out.add(new SocketIoPacket(SocketIoPacketType.CONNECT, namespace));
                    break;
                case DISCONNECT:
                    out.add(new SocketIoPacket(SocketIoPacketType.DISCONNECT, namespace));
                    break;
                case EVENT:
                case ACK:
                    Long eventId = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    if (Character.isDigit(data.charAt(i))) {
                        for (; i < data.length(); ++i) {
                            char c = data.charAt(i);
                            if (Character.isDigit(c)) {
                                stringBuilder.append(c);
                            } else {
                                break;
                            }
                        }
                    }
                    String result = stringBuilder.toString();
                    if (result.length() > 0) {
                        eventId = Long.parseLong(result);
                    }
                    out.add(new SocketIoEvent<>(type, namespace, eventId, data.substring(i)));
                    break;
                case BINARY_EVENT:
                case BINARY_ACK:
                    throw new NotImplementedException();
                case ERROR:
                    out.add(new SocketIoError(namespace, data.substring(i)));
                    break;
            }
        }
    }
}
