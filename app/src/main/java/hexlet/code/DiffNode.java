package hexlet.code;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DiffNode {

    private final String key;
    private final String type;
    private final Object value;
    private final Object previousValue;
    private final List<DiffNode> children;

    public DiffNode(String key, String type, Object value) {
        this(key, type, value, null, null);
    }

    public DiffNode(String key, String type, Object value, Object previousValue) {
        this(key, type, value, previousValue, null);
    }

    public DiffNode(String key, String type, List<DiffNode> children) {
        this(key, type, null, null, children);
    }

    private DiffNode(String key, String type, Object value, Object previousValue, List<DiffNode> children) {
        this.key = key;
        this.type = type;
        this.value = value;
        this.previousValue = previousValue;
        this.children = children;
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

    public List<DiffNode> getChildren() {
        return children;
    }
}
