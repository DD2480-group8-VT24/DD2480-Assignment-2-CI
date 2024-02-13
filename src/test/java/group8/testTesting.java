package group8;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class testTesting {

    @Test
    public void checkTestTrue() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest#testIsTrue"));
    }
    @Test
    public void checkTestFalse() {
        assertFalse(runUnitTests.runTests("-Dtest=DummyTest#testIsFalse"));
    }
    @Test(expected = IOException.class)
    public void checkTestDoesntExist() {
        runUnitTests.runTests("-Dtest=DummyTest#NoTestExists");   
    }
}
