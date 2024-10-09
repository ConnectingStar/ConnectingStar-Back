package connectingstar.tars.common.enums;

/**
 * 데이터 정렬 순서. 오름차순 / 내림차순.
 * <p>
 * Request param - sortOrder에서 사용한다.
 */
public enum SortOrder {
    ASC("asc"),
    DESC("desc");

    private final String stringValue;

    SortOrder(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static SortOrder fromString(String str) {
        for (SortOrder sortOrder : SortOrder.values()) {
            if (sortOrder.stringValue.equalsIgnoreCase(str)) {
                return sortOrder;
            }
        }
        throw new IllegalArgumentException("No enum with string " + str + " found");
    }
}
