package lexek.netty.handler.codec.basic;

import com.fasterxml.jackson.databind.JsonNode;

public class IoEvent {
    private final Long id;
    private final String namespace;
    private final String scope;
    private final JsonNode data;

    public IoEvent(String namespace, Long id, String scope, JsonNode data) {
        this.id = id;
        this.scope = scope;
        this.namespace = namespace;
        this.data = data;
    }

    public IoEvent(Long id, String scope, JsonNode data) {
        this.namespace = "/";
        this.id = id;
        this.scope = scope;
        this.data = data;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getScope() {
        return scope;
    }

    public JsonNode getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "IoEvent{" +
            "id=" + id +
            ", namespace='" + namespace + '\'' +
            ", scope='" + scope + '\'' +
            ", data=" + data +
            '}';
    }
}
