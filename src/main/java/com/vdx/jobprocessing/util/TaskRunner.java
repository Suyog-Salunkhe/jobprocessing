package com.vdx.jobprocessing.util;

import com.vdx.jobprocessing.entity.JobDetails;
import com.vdx.jobprocessing.repository.IJobDetailsRepository;
import com.vdx.jobprocessing.service.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

public class TaskRunner implements Callable<JobDetails> {

    private final Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private Job job;
    private JobDetails jobDetails;

    @Autowired
    private IJobDetailsRepository jobDetailsRepository;

    public TaskRunner(Job job, JobDetails jobDetails) {
        this.job = job;
        this.jobDetails = jobDetails;
    }

    @Override
    public JobDetails call() throws Exception {
        String status = null;
        try {
            long startTime = System.nanoTime();
            job.run();
            long endTime = System.nanoTime();
            status = "COMPLETED";
            jobDetails.setStartTime(startTime);
            jobDetails.setEndTime(endTime);
            long elapsedTime = endTime - startTime;
            jobDetails.setTotalTime(Double.parseDouble(String.format("%.5f", (double)elapsedTime/100000)));
        }catch (Exception e){
            status = "FAILED";
            jobDetails.setStartTime(null);
            logger.error("TaskRunner : call() - " + e.getMessage() );
        }
        jobDetails.setJobStatus(status);
        return jobDetails;
    }
}
