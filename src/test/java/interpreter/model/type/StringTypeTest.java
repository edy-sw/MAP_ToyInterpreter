package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {

    @Test
    void defaultValueReturnsEmptyStringValue() {
        var stringType = new StringType();
        assertEquals(new StringValue(""), stringType.defaultValue());
    }

    @Test
    void stringTypeEqualityAndToString() {
        assertEquals(new StringType(), new StringType());
        assertEquals("string", new StringType().toString());
    }

    @Test
    void stringValueEqualityAndHashCode() {
        var s1 = new StringValue("test.in");
        var s2 = new StringValue("test.in");
        var s3 = new StringValue("other.in");

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertEquals("\"test.in\"", s1.toString());
        assertEquals(new StringType(), s1.getType());
    }
}