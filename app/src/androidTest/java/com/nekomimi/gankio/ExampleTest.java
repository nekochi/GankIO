package com.nekomimi.gankio;

import android.test.InstrumentationTestCase;

import java.util.Calendar;

/**
 * Created by hongchi on 2016-1-28.
 * File description :
 */
public class ExampleTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        assertEquals(day, 29);

    }
}