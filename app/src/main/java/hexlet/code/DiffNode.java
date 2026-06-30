package hexlet.code;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DiffNode {

    private final String key;
    private final String type;
    private final Object value;
    private final Object previousValue;

    public DiffNode(String key, String type, Object value) {
        this(key, type, value, null);
    }

    public DiffNode(String key, String type, Object value, Object previousValue) {
        this.key = key;
        this.type = type;
        this.value = value;
        this.previousValue = previousValue;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Object getPreviousValue() {
        return previousValue;
    }
}
