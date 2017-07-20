package lexek.netty.handler.codec.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lexek.netty.handler.codec.socketio.SocketIoEvent;
import lexek.netty.handler.codec.socketio.SocketIoPacketType;

import java.util.List;

public class IoEventCodec extends MessageToMessageCodec<SocketIoEvent<String>, IoEvent> {
    private final ObjectMapper objectMapper;

    public IoEventCodec() {
        this(new ObjectMapper());
    }

    public IoEventCodec(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IoEvent message, List<Object> out) throws Exception {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        arrayNode.add(message.getScope());
        arrayNode.add(message.getData());
        out.add(new SocketIoEvent<>(
            SocketIoPacketType.EVENT,
            message.getNamespace(),
            message.getId(),
            objectMapper.writeValueAsString(arrayNode)
        ));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, SocketIoEvent<String> message, List<Object> out) throws Exception {
        JsonNode root = objectMapper.readTree(message.getData());
        if (message.getType() == SocketIoPacketType.EVENT) {
            out.add(new IoEvent(
                null,
                root.get(0).asText(),
                root.get(1)
            ));
        }
        if (message.getType() == SocketIoPacketType.ACK) {
            out.add(new IoAck(
                message.getId(),
                root.get(0)
            ));
        }
    }
}
