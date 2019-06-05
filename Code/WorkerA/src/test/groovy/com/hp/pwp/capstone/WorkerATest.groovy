package com.hp.pwp.capstone

import org.junit.Test

public class WorkerATest extends GroovyTestCase {
    @Test
    void testIfTrueisWhatItSaysItIs() {
        Convert conv = new Convert()
        boolean tru = true
        assert(tru);
    }
}
