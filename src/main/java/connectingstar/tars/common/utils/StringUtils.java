package connectingstar.tars.common.utils;

public class StringUtils {
    /**
     * "camelCase" -> "CAMEL_CASE"
     */
    public static String camelToUpperCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // if already in upper case, return as is
        if (input.equals(input.toUpperCase())) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(input.charAt(0)));

        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append('_');
            }
            result.append(Character.toUpperCase(currentChar));
        }

        return result.toString();
    }
}
