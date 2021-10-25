package com.vdx.jobprocessing.vo;

public class JobStats {

    private int jobsReceived;
    private String averageProcessingTime;
    private double jobSuccessRate;
    private double jobFailureRate;

    public JobStats(int jobsReceived) {
        this.jobsReceived = jobsReceived;
    }

    public int getJobsReceived() {
        return jobsReceived;
    }

    public void setJobsReceived(int jobsReceived) {
        this.jobsReceived = jobsReceived;
    }

    public String getAverageProcessingTime() {
        return averageProcessingTime;
    }

    public void setAverageProcessingTime(double averageProcessingTime) {
        this.averageProcessingTime = new Double(averageProcessingTime).toString();
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
