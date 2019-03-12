package com.indev.job.scheduling.simulation;

public class RunResult {
    private final double averageWaitingTime;
    private final double averageTurnAroundTime;
    private final double averageResponseTime;

    public RunResult(double averageWaitingTime, double averageTurnAroundTime, double averageResponseTime) {

        this.averageWaitingTime = averageWaitingTime;
        this.averageTurnAroundTime = averageTurnAroundTime;
        this.averageResponseTime = averageResponseTime;
    }

    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }
}
