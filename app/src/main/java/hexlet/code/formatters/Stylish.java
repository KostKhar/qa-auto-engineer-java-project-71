package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;

public final class Stylish {

    private Stylish() {
    }

    public static String render(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (DiffNode node : diff) {
            result.append(formatNode(node));
        }

        result.append("}");
        return result.toString();
    }

    private static String formatNode(DiffNode node) {
        return switch (node.getType()) {
            case "added" -> "  + " + node.getKey() + ": "
                    + formatValue(node.getValue())
                    + "\n";
            case "deleted" -> "  - " + node.getKey() + ": "
                    + formatValue(node.getValue())
                    + "\n";
            case "changed" -> "  - " + node.getKey() + ": "
                    + formatValue(node.getPreviousValue())
                    + "\n"
                    + "  + " + node.getKey() + ": "
                    + formatValue(node.getValue())
                    + "\n";
            case "unchanged" -> "    " + node.getKey() + ": "
                    + formatValue(node.getValue())
                    + "\n";
            default -> "";
        };
    }

    private static String formatValue(Object value) {
        return String.valueOf(value);
    }
}
