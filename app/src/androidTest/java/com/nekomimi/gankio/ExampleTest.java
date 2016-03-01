package com.nekomimi.gankio;

import android.test.InstrumentationTestCase;

/**
 * Created by hongchi on 2016-1-28.
 * File description :
 */
public class ExampleTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}