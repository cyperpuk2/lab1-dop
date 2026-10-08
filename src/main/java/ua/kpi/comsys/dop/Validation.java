package ua.kpi.comsys.dop;

/**
 * Допоміжні перевірки для компактних конструкторів record-ів.
 */
final class Validation {

    private Validation() {
    }

    static void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be null or blank");
        }
    }

    static void requirePositive(int value, String field) {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }
}
