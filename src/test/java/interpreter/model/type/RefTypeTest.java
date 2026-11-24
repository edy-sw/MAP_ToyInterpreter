package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void defaultValueIsAddressZeroWithInnerType() {
        IType refInt = new RefType(new IntType());
        assertEquals(new RefValue(0, new IntType()), refInt.defaultValue());
    }

    @Test
    void nestedRefTypesCompareStructurally() {
        IType refInt = new RefType(new IntType());
        IType refRefInt1 = new RefType(new RefType(new IntType()));
        IType refRefInt2 = new RefType(new RefType(new IntType()));

        assertEquals(refRefInt1, refRefInt2);
        assertNotEquals(refInt, refRefInt1);
    }

    @Test
    void refValueGetTypeWrapsLocationTypeInRefType() {
        RefValue val = new RefValue(1, new IntType());
        assertEquals(new RefType(new IntType()), val.getType());
        assertEquals("(1,int)", val.toString());
    }
}