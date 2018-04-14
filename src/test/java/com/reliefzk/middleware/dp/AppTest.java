package com.reliefzk.middleware.dp;

import com.reliefzk.middleware.dp.pipeline.PipelineContext;
import com.reliefzk.middleware.dp.pipeline.Valve;
import com.reliefzk.middleware.dp.extension.OrderUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test(dataProvider = "testShuffleData")
    public void testShuffule(Valve[] valves) {
        valves = OrderUtils.shuffleOrder(valves);
        Assert.assertTrue(valves[0].order()< valves[1].order());
    }

    @DataProvider
    private Object[][] testShuffleData(){
        Valve valve1 = new Valve() {
            @Override
            public void invoke(PipelineContext pipelineContext) throws Exception { }
            @Override
            public int order() {
                return 1;
            }
        };

        Valve valve2 = new Valve() {
            @Override
            public void invoke(PipelineContext pipelineContext) throws Exception { }
            @Override
            public int order() {
                return 2;
            }
        };


        return new Object[][]{
                { valve1, valve2 },
                { valve2, valve1 },
        };
    }

}
