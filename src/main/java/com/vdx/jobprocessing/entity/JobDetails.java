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

