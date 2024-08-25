package connectingstar.tars.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringUtilsTest {
    @Test
    void camelToUpperCaseReturnsNullForNullInput() {
        assertNull(StringUtils.camelToUpperCase(null));
    }

    @Test
    void camelToUpperCaseReturnsEmptyForEmptyString() {
        assertEquals("", StringUtils.camelToUpperCase(""));
    }

    @Test
    void camelToUpperCaseConvertsSingleWord() {
        assertEquals("CAMEL", StringUtils.camelToUpperCase("camel"));
    }

    @Test
    void camelToUpperCaseConvertsMultipleWords() {
        assertEquals("CAMEL_CASE", StringUtils.camelToUpperCase("camelCase"));
    }

    @Test
    void camelToUpperCaseHandlesAllUpperCaseInput() {
        assertEquals("CAMEL_CASE", StringUtils.camelToUpperCase("CAMEL_CASE"));
    }

    @Test
    void camelToUpperCaseHandlesMixedCaseInput() {
        assertEquals("CAMEL_CASE_TEST", StringUtils.camelToUpperCase("camelCaseTest"));
    }

    @Test
    void camelToUpperCaseHandlesLeadingUpperCase() {
        assertEquals("CAMEL_CASE", StringUtils.camelToUpperCase("CamelCase"));
    }
}
