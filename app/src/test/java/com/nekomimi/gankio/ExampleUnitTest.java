package com.nekomimi.gankio;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void multiply_isCorrect() throws Exception{
        assertThat(4*4,equalTo(16));
    }
}