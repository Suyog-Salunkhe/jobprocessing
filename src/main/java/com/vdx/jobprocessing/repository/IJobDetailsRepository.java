package com.vdx.jobprocessing.repository;

import com.vdx.jobprocessing.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobDetailsRepository extends JpaRepository <JobDetails, Long> {

    List<JobDetails> findJobDetailsByJobStatusAndStatus(String jobStatus, String status);

    @Query("select jd from JobDetails jd where jd.jobStatus not in (:jobStatus) and jd.status = 'ACTIVE'")
    List<JobDetails> findJobDetailsByJobStatusNotInAndStatus(List<String> jobStatus);
}
