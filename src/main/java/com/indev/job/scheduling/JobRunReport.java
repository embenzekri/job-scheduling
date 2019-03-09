package com.indev.job.scheduling;

public class JobRunReport {

    private final int startTime;
    private final int completionTime;

    public JobRunReport(int startTime, int completionTime) {
        this.startTime = startTime;
        this.completionTime = completionTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getStartTime() {
        return startTime;
    }
}
