package com.indev.job.scheduling.scheduler;

public class Job {
    private String processName;
    private int arrivalTime;
    private int serviceTime;
    private int priorityLevel;
    private int waitingTime;
    private int turnaroundTime;
    private int responseTime;

    private Job(String processName, int arrivalTime, int serviceTime, int priorityLevel, int waitingTime, int turnaroundTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.priorityLevel = priorityLevel;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
    }

    public Job(String processName, int arrivalTime, int serviceTime, int priorityLevel) {
        this(processName, arrivalTime, serviceTime, priorityLevel, 0, 0);
    }

    public Job(String processName, int arrivalTime, int serviceTime) {
        this(processName, arrivalTime, serviceTime, 0, 0, 0);
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
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

    public int getServiceTime() {
        return this.serviceTime;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public int getWaitingTime() {
        if (this.waitingTime < 0)
            return 0;
        return this.waitingTime;
    }

    public int getTurnaroundTime() {
        return this.turnaroundTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
}
