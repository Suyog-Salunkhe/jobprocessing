package com.vdx.jobprocessing.service;

import org.springframework.stereotype.Service;

@Service
public interface JobProcessorSystem {

    String run(Job job, long futureExecutionTimesInMillis);
}
