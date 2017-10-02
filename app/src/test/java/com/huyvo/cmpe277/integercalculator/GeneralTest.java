package com.huyvo.cmpe277.integercalculator;

import com.huyvo.cmpe277.integercalculator.model.TokenModel;


import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Huy Vo on 9/28/17.
 */

public class GeneralTest {

    @Test
    public void test_token_model() throws Exception{
        TokenModel tokenModel = new TokenModel("-123");

        assertEquals(tokenModel.getIntegerValue(), -123);
        assertEquals(tokenModel.toString(), "-123");
    }
}
