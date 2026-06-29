package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    static {
        JSON_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        YAML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private Parser() {
    }

    public static Map<String, Object> parse(String content, String format) throws IllegalArgumentException, JsonProcessingException {
        if (content == null) {
            throw new IllegalArgumentException("Content must not be null");
        }
        if (format == null) {
            throw new IllegalArgumentException("Format must not be null");
        }

        if (content.trim().isEmpty()) {
            return new LinkedHashMap<>();
        }

        ObjectMapper mapper = resolveMapper(format);
        Map<String, Object> data = mapper.readValue(content, new TypeReference<>() { });
        return sortByKeys(data);
    }

    private static ObjectMapper resolveMapper(String format) {
        return switch (format.toLowerCase()) {
            case "yaml", "yml" -> YAML_MAPPER;
            case "json" -> JSON_MAPPER;
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    private static LinkedHashMap<String, Object> sortByKeys(Map<String, Object> map) {
        LinkedHashMap<String, Object> sorted = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));
        return sorted;
    }
}
