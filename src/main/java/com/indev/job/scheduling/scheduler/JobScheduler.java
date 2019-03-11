package com.indev.job.scheduling.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JobScheduler {
    private List<Job> jobs;
    private List<Event> timeline;
    private int timeQuantum;

    public JobScheduler() {
        jobs = new ArrayList();
        timeline = new ArrayList();
        timeQuantum = 1;
    }

    public boolean add(Job job) {
        return jobs.add(job);
    }

    public void clearForNextRun() {
        jobs = new ArrayList();
        timeline = new ArrayList();
    }

    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public double getAverageWaitingTime() {
        double avg = 0.0;

        for (Job job : jobs) {
            avg += job.getWaitingTime();
        }

        return avg / jobs.size();
    }

    public double getAverageTurnAroundTime() {
        double avg = 0.0;

        for (Job job : jobs) {
            avg += job.getTurnaroundTime();
        }

        return avg / jobs.size();
    }

    public Event getEvent(Job job) {
        for (Event event : timeline) {
            if (job.getProcessName().equals(event.getProcessName())) {
                return event;
            }
        }

        return null;
    }

    public Job getRow(String process) {
        for (Job job : jobs) {
            if (job.getProcessName().equals(process)) {
                return job;
            }
        }

        return null;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public List<Event> getTimeline() {
        return timeline;
    }

    public void process() {
        Collections.sort(this.getJobs(), (Object o1, Object o2) -> {
            if (((Job) o1).getArrivalTime() == ((Job) o2).getArrivalTime()) {
                return 0;
            } else if (((Job) o1).getArrivalTime() < ((Job) o2).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        doProcess();
    }

    public abstract void doProcess();
}
