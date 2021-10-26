package com.vdx.jobprocessing;


import com.vdx.jobprocessing.service.Schedular;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedularTest extends EndToEndTest {

    @Autowired
    private Schedular schedular;

    @Test
    public void testSchedular(){
        schedular.runJobs();
    }
}
