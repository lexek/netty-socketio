package lexek.netty.handler.codec.socketio;

import com.fasterxml.jackson.databind.JsonNode;

public class SocketIoJsonData {
    private final String room;
    private final JsonNode json;

    public SocketIoJsonData(String room, JsonNode json) {
        this.room = room;
        this.json = json;
    }

    public String getRoom() {
        return room;
    }

    public JsonNode getJson() {
        return json;
    }
}
