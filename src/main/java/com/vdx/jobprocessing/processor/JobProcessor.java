package com.vdx.jobprocessing.processor;

import com.vdx.jobprocessing.entity.JobDetails;
import com.vdx.jobprocessing.repository.IJobDetailsRepository;
import com.vdx.jobprocessing.service.JobProcessorSystem;
import com.vdx.jobprocessing.vo.JobStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JobProcessor {

    private final Logger logger = LoggerFactory.getLogger(JobProcessor.class);

    private final static String filePath = "/home/suyog/Desktop/stats.csv";

    @Autowired
    private JobProcessorSystem jobProcessorSystem;

    @Autowired
    private IJobDetailsRepository jobDetailsRepository;

    @Transactional
    public void processJob() {
        List<JobDetails> jobDetails = jobDetailsRepository.findJobDetailsByJobStatusAndStatus("PENDING", "ACTIVE");

        if (jobDetails.size() > 0) {
            jobDetails.stream().forEach(job -> {
                job.setJobStatus("PROCESSING");
                job.setStartProcessingTimeStamp(LocalDateTime.now().toString());
            });
            jobDetailsRepository.saveAllAndFlush(jobDetails);
        }
        try {
            for (JobDetails jobDetail : jobDetails) {
                try {
                    jobProcessorSystem.run(jobDetail);
                } catch (Exception cfe) {
                    logger.error("No such job found : " + jobDetail.getJobTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("JobProcessor - processJob() : " + e.getMessage());
        }
    }

    @Transactional
    public void storeStatsInFile() {
        List<JobDetails> jobDetails = jobDetailsRepository.findJobDetailsByJobStatusNotInAndStatus(Arrays.asList("PENDING", "PROCESSING"));
        Map<String, List<JobDetails>> map = jobDetails.stream().collect(Collectors.groupingBy(JobDetails::getStartProcessingTimeStamp, Collectors.toList()));
        Collection<JobStats> list = map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new JobStats(e.getValue().size(), e.getValue().stream().mapToDouble(i->i.getTotalTime()).average().orElse(0.0),
                        (double)e.getValue().stream().filter(i -> i.getJobStatus().equals("COMPLETED")).collect(Collectors.toList()).size() / (double)e.getValue().size(),
                        (double)e.getValue().stream().filter(i -> i.getJobStatus().equals("FAILED")).collect(Collectors.toList()).size() / (double)e.getValue().size()))).values();
        try {
            File file = new File(filePath);
            if(file.exists()) {
                printCustomerList(file.getPath(), list);
                jobDetails.stream().forEach(j -> j.setStatus("INACTIVE"));
                jobDetailsRepository.saveAllAndFlush(jobDetails);
            }
        } catch (FileNotFoundException f) {
            logger.error("storeStatsInFile : Stats file not found");
        } catch (IOException i) {
            logger.error("storeStatsInFile : Error while writing stats");
        }
    }

    public void printCustomerList(String path, Collection<JobStats> jobStatsList) throws IOException{
        try {
            FileWriter pw = new FileWriter(path, true);
            Iterator s = jobStatsList.iterator();
            if (s.hasNext() == false) {
                logger.info("File is empty");
            }
            while (s.hasNext()) {
                JobStats current = (JobStats) s.next();
                System.out.println(current.toString() + "\n");
                pw.append(String.valueOf(current.getJobsReceived()));
                pw.append(",");
                pw.append(String.valueOf(current.getAverageProcessingTime()));
                pw.append(",");
                pw.append(String.valueOf(current.getJobSuccessRate()));
                pw.append(",");
                pw.append(String.valueOf(current.getJobFailureRate()));
                pw.append("\n");
            }
            pw.close();
        }catch (Exception e){
           logger.error(e.getMessage());
        }
    }
}