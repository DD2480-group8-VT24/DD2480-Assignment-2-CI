package group8;

import org.junit.Test;
import static org.junit.Assert.*;

public class testTesting {

    @Test
    public void checkTestTrue() {
        assertTrue(runUnitTests.runTests("-Dtest=DummyTest"));
    }
}
