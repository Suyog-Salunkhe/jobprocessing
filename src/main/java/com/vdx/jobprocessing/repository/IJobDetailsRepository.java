package com.vdx.jobprocessing.repository;

import com.vdx.jobprocessing.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobDetailsRepository extends JpaRepository <JobDetails, Long> {

    List<JobDetails> findJobDetailsByJobStatusAndStatus(String jobStatus, String status);
}
