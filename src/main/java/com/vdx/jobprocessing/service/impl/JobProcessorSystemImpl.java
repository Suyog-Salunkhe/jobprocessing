package com.vdx.jobprocessing.service.impl;

import com.vdx.jobprocessing.entity.JobDetails;
import com.vdx.jobprocessing.repository.IJobDetailsRepository;
import com.vdx.jobprocessing.service.Job;
import com.vdx.jobprocessing.service.JobProcessorSystem;
import com.vdx.jobprocessing.util.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
public class JobProcessorSystemImpl implements JobProcessorSystem {

    private Logger logger = LoggerFactory.getLogger(JobProcessorSystemImpl.class);
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    private final static String JOB_CLASS_PATH = "com.vdx.jobprocessing.impl.";
    @Autowired
    private IJobDetailsRepository jobDetailsRepository;

    @Override
    @Transactional
    public void run(JobDetails jobDetail) {
        try {
            Class jobClass = Class.forName(JOB_CLASS_PATH+ jobDetail.getJobTitle());
            Job job = (Job) jobClass.newInstance();
            if(job.initialize()) {
                Future<JobDetails> jobDetailsScheduledFuture = scheduler.schedule(new TaskRunner(job, jobDetail), jobDetail.getFutureExecutionTime(), TimeUnit.MILLISECONDS);
                System.out.println(jobDetailsScheduledFuture.get().getJobStatus());
                jobDetailsRepository.saveAndFlush(jobDetailsScheduledFuture.get());
            }
        } catch (ClassNotFoundException cfe) {
            logger.error("No such job found : " + jobDetail.getJobTitle());
        } catch (Exception e) {
            logger.error("JobProcessionSystemImpl : " + e.getMessage());
        }
    }
}
