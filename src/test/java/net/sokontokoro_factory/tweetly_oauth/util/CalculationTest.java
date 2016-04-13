package net.sokontokoro_factory.tweetly_oauth.util;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by TATSUYA-PC4 on 2016/04/10.
 */
public class CalculationTest {

    @Test
    public void HmacSHA1形式で暗号化出来ること() throws Exception {
        String data = "d";
        String key = "k";
        int actual = ByteBuffer.wrap(Calculation.calcHmacSHA1(data, key)).getInt();
        int expected = 730915870;
        assertThat(actual, is(expected));
    }

    @Test
    public void Timestampを取得できる() throws Exception {
        String actual = Calculation.getTimestamp();
        try{
            Integer.parseInt(actual);
        }catch(NumberFormatException e){
            fail();
        }
    }

    @Test
    public void 任意かつ一意の文字列を取得できる() throws Exception {
        String actuals = Calculation.getNonce();
        int NumberOfTrils = 10;

        for(int i = 0; i < NumberOfTrils; i++){
            assertThat(actuals, not(Calculation.getNonce()));
        }
    }
}