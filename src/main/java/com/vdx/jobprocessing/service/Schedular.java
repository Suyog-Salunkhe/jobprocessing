package com.vdx.jobprocessing.service;

import com.vdx.jobprocessing.processor.JobProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class Schedular {

    @Autowired
    private JobProcessor jobProcessor;

    @Scheduled(cron = "${run-jobs}")
    @Transactional
    public void runJobs(){
        jobProcessor.processJob();
    }
}
