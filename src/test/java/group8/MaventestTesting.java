package group8;

import org.junit.Test;
import static org.junit.Assert.*;

/*
 * This class will run the unit tests for the Testing part with maven of the code. 
 */
public class MaventestTesting {

    @Test
    public void checkTestTrue() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest#testIsTrue"));
    }
    @Test
    public void checkTestFalse() {
        assertFalse(runUnitTests.runTests("-Dtest=DummyTest#testIsFalse"));
    }
    @Test
    public void checkAllTests() {
        assertTrue(runUnitTests.runAllTests());
    }
}
