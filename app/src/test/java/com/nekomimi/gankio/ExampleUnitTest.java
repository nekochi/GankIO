package com.nekomimi.gankio;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    @Test
    public void addition_isCorrect() throws Exception {
        long d = Long.parseLong("572DE80967765974FBFCFA26", 16);
        assertEquals(CreateTemporaryLicensePassword(909090),"6F6F6F"  );
    }
    @Test
    public void multiply_isCorrect() throws Exception{
        assertThat(4 * 4, equalTo(16));
    }

    public String CreateTemporaryLicensePassword(int random)
    {
        String result = "";
        if(random<100000 || random > 999999)
        {
            return null;
        }
        int password[] = new int[6];
        password[0] =  0x0000000F & (~ (random/100000));
        password[1] =  0x0000000F & (~ (random%100000/10000));
        password[2] =  0x0000000F & (~ (random%10000/1000));
        password[3] =  0x0000000F & (~ (random%1000/100));
        password[4] =  0x0000000F & (~ (random%100/10));
        password[5] =  0x0000000F & (~ (random%10));

        int bit = 0;
        if(random%2 == 0)
        {
            bit = password[0];
            password[0] = password[4];
            password[4] = bit;
            bit = password[1];
            password[1] = password[5];
            password[5] = bit;
        }else {
            bit = password[2];
            password[2] = password[4];
            password[4] = bit;
            bit = password[3];
            password[3] = password[5];
            password[5] = bit;
        }
        for (int i : password)
        {
            result = result + Integer.toHexString(i);
        }

        if (result.length() == 6) {
            return result.toUpperCase();
        }else
        {
            return null;
        }
    }
}