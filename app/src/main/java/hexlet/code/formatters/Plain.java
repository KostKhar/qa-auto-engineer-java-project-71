package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;

public final class Plain {

    private static final String PROPERTY_WAS_UPDATED = "Property '%s' was updated. From %s to %s";
    private static final String PROPERTY_WAS_ADDED = "Property '%s' was added with value: %s";
    private static final String PROPERTY_WAS_DELETED = "Property '%s' was removed";

    private Plain() {
    }

    public static String render(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder();

        for (DiffNode node : diff) {
            String line = formatNode(node);
            if (line != null) {
                result.append(line).append("\n");
            }
        }

        String output = result.toString();
        if (output.endsWith("\n")) {
            return output.substring(0, output.length() - 1);
        }
        return output;
    }

    private static String formatNode(DiffNode node) {
        return switch (node.getType()) {
            case "added" -> String.format(PROPERTY_WAS_ADDED, node.getKey(), formatValue(node.getValue()));
            case "deleted" -> String.format(PROPERTY_WAS_DELETED, node.getKey());
            case "changed" -> String.format(
                    PROPERTY_WAS_UPDATED,
                    node.getKey(),
                    formatValue(node.getPreviousValue()),
                    formatValue(node.getValue()));
            default -> null;
        };
    }

    private static String formatValue(Object value) {
        if (value instanceof String) {
            return String.format("'%s'", value);
        } else if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        } else if (value == null) {
            return "null";
        } else {
            return "[complex value]";
        }
    }
}
