package com.vdx.jobprocessing.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "job_details")
public class JobDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "JOB_DETAILS_ID", unique = true, nullable = false)
    private Long jobDetailsId;

    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "JOB_STATUS")
    private String jobStatus;

    @Column(name = "START_PROCESSING_TIMESTAMP")
    private String startProcessingTimeStamp;

    @Column(name = "FUTURE_EXECUTION_TIME")
    private Long futureExecutionTime;

    @Column(name = "START_TIME")
    private Long startTime;

    @Column(name = "END_TIME")
    private Long endTime;

    @Column(name = "TOTAL_TIME")
    private Double totalTime;

    @Column(name = "STATUS", nullable = false, length = 10)
    private String status;

    @CreationTimestamp
    @Column(name = "CREATE_DTTM", length = 19)
    protected LocalDateTime createDttm;

    @UpdateTimestamp
    @Column(name = "UPDATE_DTTM", length = 19, nullable = true)
    protected LocalDateTime updateDttm;

    public Long getJobDetailsId() {
        return jobDetailsId;
    }

    public void setJobDetailsId(Long jobDetailsId) {
        this.jobDetailsId = jobDetailsId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getStartProcessingTimeStamp() {
        return startProcessingTimeStamp;
    }

    public void setStartProcessingTimeStamp(String startProcessingTimeStamp) {
        this.startProcessingTimeStamp = startProcessingTimeStamp;
    }

    public long getFutureExecutionTime() {
        return futureExecutionTime;
    }

    public void setFutureExecutionTime(long futureExecutionTime) {
        this.futureExecutionTime = futureExecutionTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setFutureExecutionTime(Long futureExecutionTime) {
        this.futureExecutionTime = futureExecutionTime;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDttm() {
        return createDttm;
    }

    public void setCreateDttm(LocalDateTime createDttm) {
        this.createDttm = createDttm;
    }

    public LocalDateTime getUpdateDttm() {
        return updateDttm;
    }

    public void setUpdateDttm(LocalDateTime updateDttm) {
        this.updateDttm = updateDttm;
    }
}

