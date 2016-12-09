/*
 * Created by Mohamed Bushra on 07/12/16 22:09
 * Copyright (c) 2016. All rights reserved.
 *
 *  Last modified 07/12/16 21:21
 */

package uk.ac.brunel.tunel;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("uk.ac.brunel.tunel", appContext.getPackageName());
    }
}
