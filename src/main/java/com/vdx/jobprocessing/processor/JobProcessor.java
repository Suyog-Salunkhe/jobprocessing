package com.vdx.jobprocessing.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vdx.jobprocessing.entity.JobDetails;
import com.vdx.jobprocessing.repository.IJobDetailsRepository;
import com.vdx.jobprocessing.service.Job;
import com.vdx.jobprocessing.service.JobProcessorSystem;
import com.vdx.jobprocessing.vo.JobStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.Resource;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JobProcessor {

    private final Logger logger = LoggerFactory.getLogger(JobProcessor.class);
    @Autowired
    private JobProcessorSystem jobProcessorSystem;

    @Autowired
    private IJobDetailsRepository jobDetailsRepository;

    @Value("${location}")
    private Resource templateLocation;

    @Transactional
    public void processJob() {
        List<JobDetails> jobDetails = jobDetailsRepository.findJobDetailsByJobStatusAndStatus("PENDING", "ACTIVE");

        JobStats jobStats = new JobStats(jobDetails.size());
        try {
            List<Double> elapsedTimeList = new ArrayList<>();
            List<String> jobSuccessList = new ArrayList<>();
            for (JobDetails jobDetail : jobDetails) {
                try {
                    Class jobClass = Class.forName("com.vdx.jobprocessing.impl." + jobDetail.getJobTitle());
                    Job job = (Job) jobClass.newInstance();
                    long startTime = System.nanoTime();
                    String status = jobProcessorSystem.run(job, 0);
                    jobSuccessList.add(status);
                    timeStats(startTime, elapsedTimeList);
                    updateJobDetailsStatus(jobDetail, status);
                } catch (ClassNotFoundException cfe) {
                    updateJobDetailsStatus(jobDetail, "FAILED");
                    logger.error("No such job found : " + jobDetail.getJobTitle());
                }
            }
            resultStats(jobSuccessList, elapsedTimeList, jobStats, jobDetails.size());
            storeStatsInFile(jobStats);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("JobProcessor - processJob() : " + e.getMessage());
        }
    }

    private void timeStats(long startTime, List<Double> elapsedTimeList) {
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
        elapsedTimeList.add(elapsedTimeInSecond);
    }

    private void resultStats(List<String> jobSuccessList, List<Double> elapsedTimeList, JobStats jobStats, int jobDetailsSize) {
        DoubleSummaryStatistics stats = elapsedTimeList.stream().mapToDouble(time -> time).summaryStatistics();
        jobStats.setAverageProcessingTime(stats.getAverage());
        Map<String, Long> result = jobSuccessList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (result.containsKey("COMPLETED")) {
            jobStats.setJobSuccessRate(result.get("COMPLETED").doubleValue() / jobDetailsSize);
        }
        if (result.containsKey("FAILED")) {
            jobStats.setJobFailureRate(result.get("FAILED").doubleValue() / jobDetailsSize);
        }
    }

    @Transactional
    public void updateJobDetailsStatus(JobDetails jobDetail, String status) {
        jobDetail.setJobStatus(status);
        jobDetail.setUpdateDttm(LocalDateTime.now());
        jobDetailsRepository.saveAndFlush(jobDetail);
    }

    public void storeStatsInFile(JobStats jobStats) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            File file = new File("/home/suyog/VDX/jobprocessing/stats.json");
            objectMapper.writeValue(file, jobStats);
        } catch (FileNotFoundException f) {
            logger.error("storeStatsInFile : Stats file not found");
        } catch (IOException i) {
            logger.error("storeStatsInFile : Error while writing stats");
        }
    }
}
