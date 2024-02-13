package group8;

import org.junit.Test;
import static org.junit.Assert.*;


public class MaventestTesting {

    @Test
    public void checkTestTrue() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest#testIsTrue"));
    }
    @Test
    public void checkTestFalse() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest#testIsFalse"));
    }
    @Test
    public void checkAllTests() {
        assertFalse(runUnitTests.runTests(""));
    }
}
