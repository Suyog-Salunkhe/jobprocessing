package com.vdx.jobprocessing.vo;

public class JobStats {

    private Integer jobsReceived;
    private Double averageProcessingTime;
    private Double jobSuccessRate;
    private Double jobFailureRate;

    public JobStats(Integer jobsReceived, double averageProcessingTime, double jobSuccessRate, double jobFailureRate) {
        this.jobsReceived = jobsReceived;
        this.averageProcessingTime = averageProcessingTime;
        this.jobSuccessRate = jobSuccessRate;
        this.jobFailureRate = jobFailureRate;
    }

    public JobStats(int jobsReceived) {
        this.jobsReceived = jobsReceived;
    }

    public int getJobsReceived() {
        return jobsReceived;
    }

    public void setJobsReceived(int jobsReceived) {
        this.jobsReceived = jobsReceived;
    }

    public double getAverageProcessingTime() {
        return averageProcessingTime;
    }

    public void setAverageProcessingTime(double averageProcessingTime) {
        this.averageProcessingTime = averageProcessingTime;
    }

    public double getJobSuccessRate() {
        return jobSuccessRate;
    }

    public void setJobSuccessRate(double jobSuccessRate) {
        this.jobSuccessRate = jobSuccessRate;
    }

    public double getJobFailureRate() {
        return jobFailureRate;
    }

    public void setJobFailureRate(double jobFailureRate) {
        this.jobFailureRate = jobFailureRate;
    }
}
