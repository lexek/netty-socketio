package lexek.netty.handler.codec.basic;

import com.fasterxml.jackson.databind.JsonNode;

public class IoAck {
    private final long id;
    private final JsonNode data;

    public IoAck(long id, JsonNode data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public JsonNode getData() {
        return data;
    }

    @Override
    public String toString() {
        return "IoAck{" +
            "id=" + id +
            ", data=" + data +
            '}';
    }
}
