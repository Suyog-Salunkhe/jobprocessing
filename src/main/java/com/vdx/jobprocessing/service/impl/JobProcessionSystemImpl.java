package com.vdx.jobprocessing.service.impl;

import com.vdx.jobprocessing.service.Job;
import com.vdx.jobprocessing.service.JobProcessorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class JobProcessionSystemImpl implements JobProcessorSystem {

    private Logger logger = LoggerFactory.getLogger(JobProcessionSystemImpl.class);

    @Override
    public String run(Job job, long futureExecutionTimesInMillis) {
        String status = null;
        try {
            job.run(); // run job
            status = "COMPLETED";
        } catch (Exception e) {
            logger.error("JobProcessionSystemImpl : " + e.getMessage());
            status = "FAILED";
        }
        return status;
    }
}
