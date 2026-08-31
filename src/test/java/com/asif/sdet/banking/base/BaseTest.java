package com.asif.sdet.banking.base;

import org.junit.After;
import org.junit.Before;

public abstract class BaseTest {

    protected long testStartTime;

    @Before
    public void baseSetUp() {
        testStartTime = System.currentTimeMillis();
    }

    @After
    public void baseTearDown() {
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println(getClass().getSimpleName() + " completed in " + duration + " ms");
    }
}
