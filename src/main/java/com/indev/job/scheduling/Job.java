package com.indev.job.scheduling;

public class Job {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priorityLevel;
    private int waitingTime;
    private int turnaroundTime;
    private int startTime;

    private Job(String processName, int arrivalTime, int burstTime, int priorityLevel, int waitingTime, int turnaroundTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityLevel = priorityLevel;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }

    public Job(String processName, int arrivalTime, int burstTime, int priorityLevel) {
        this(processName, arrivalTime, burstTime, priorityLevel, 0, 0);
    }

    public Job(String processName, int arrivalTime, int burstTime) {
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public String getProcessName() {
        return this.processName;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public int getWaitingTime() {
        return this.startTime - this.arrivalTime;
    }

    public int getTurnaroundTime() {
        return this.waitingTime + this.burstTime;
    }

    public JobRunReport run(int startTime) {
        this.startTime = startTime;
        int completionTime = startTime + burstTime;
        return new JobRunReport(startTime, completionTime);
    }
}
