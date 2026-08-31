package com.asif.sdet.banking.unit;

import com.asif.sdet.banking.constants.FrameworkConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrameworkConstantsTest {

    @Test
    public void shouldExposeFrameworkConstants() {
        assertEquals("chrome", FrameworkConstants.DEFAULT_BROWSER);
        assertEquals(10, FrameworkConstants.DEFAULT_WAIT_SECONDS);
    }
}
