package com.nekomimi.gankio;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    String x, y;
    @Test
    public void addition_isCorrect() throws Exception {
        Number number = 6.8;
        int value = number.intValue();
        System.out.print(value);
    }
    @Test
    public void multiply_isCorrect() throws Exception{
        assertThat(4 * 4, equalTo(16));
    }


}