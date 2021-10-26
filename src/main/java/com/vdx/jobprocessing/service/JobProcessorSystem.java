package com.vdx.jobprocessing.service;

import com.vdx.jobprocessing.entity.JobDetails;
import org.springframework.stereotype.Service;

@Service
public interface JobProcessorSystem {

    void run(JobDetails jobDetail);
}
