package com.indev.job.scheduling.simulation;

public class RunResult {
    private final double averageWaitingTime;
    private final double averageTurnAroundTime;

    public RunResult(double averageWaitingTime, double averageTurnAroundTime) {

        this.averageWaitingTime = averageWaitingTime;
        this.averageTurnAroundTime = averageTurnAroundTime;
    }

    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
}
