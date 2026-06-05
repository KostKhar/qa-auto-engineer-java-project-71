package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;


public final class FileParser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final LinkedHashMap<String, Object> EMPTY_MAP = new LinkedHashMap<>();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    static {
        JSON_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        YAML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    private FileParser() {
    }

    private static LinkedHashMap<String, Object> getData(String content, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.readValue(content, new TypeReference<>() {
        });
    }


    private static Path resolvePath(String path) {
        String userDir = System.getProperty("user.dir");

        String[] basePaths = {
                userDir,                                          // Текущая директория
                userDir + "/src/main/resources",                 // Для продакшн ресурсов
                userDir + "/src/test/resources",                 // Для тестовых ресурсов

        };

        for (String basePath : basePaths) {
            Path fullPath = Paths.get(basePath).resolve(path);
            if (Files.exists(fullPath)) {
                return fullPath.normalize();
            }
        }

        return Paths.get(userDir).resolve(path).normalize();
    }

    private static LinkedHashMap<String, Object> sortByKeys(Map<String, Object> map) {
        LinkedHashMap<String, Object> sorted = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));
        return sorted;
    }


    public static Map<String, Object> parse(String filepath) {
        if (filepath == null) {
            throw new IllegalArgumentException("File argument must not be null");
        }
        try {
            Path absolutePath = resolvePath(filepath);

            String fileName = absolutePath.getFileName().toString();

            Map<String, Object> data = null;
            String fileAsString = Files.readString(absolutePath);

            if (fileAsString.trim().isEmpty()) {
                return EMPTY_MAP;
            }

            if (fileName.endsWith(".yml")) {
                data = getData(fileAsString, YAML_MAPPER);
            } else {
                data = getData(fileAsString, JSON_MAPPER);
            }

            return sortByKeys(data);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading file: " + e.getMessage());
        }
    }

}
