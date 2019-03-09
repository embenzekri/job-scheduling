package com.indev.job.scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobScheduler {
    private final List<Job> jobs;
    private final List<Event> timeline;
    private int timeQuantum;
    private JobSchedulerStrategy strategy;

    public JobScheduler() {
        jobs = new ArrayList();
        timeline = new ArrayList();
        timeQuantum = 1;
    }

    public void setStrategy(JobSchedulerStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean add(Job row) {
        return jobs.add(row);
    }

    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public double getAverageWaitingTime() {
        double avg = 0.0;

        for (Job row : jobs) {
            avg += row.getWaitingTime();
        }

        return avg / jobs.size();
    }

    public double getAverageTurnAroundTime() {
        double avg = 0.0;

        for (Job row : jobs) {
            avg += row.getTurnaroundTime();
        }

        return avg / jobs.size();
    }

    public Event getEvent(Job row) {
        for (Event event : timeline) {
            if (row.getProcessName().equals(event.getProcessName())) {
                return event;
            }
        }

        return null;
    }

    public Job getRow(String process) {
        for (Job row : jobs) {
            if (row.getProcessName().equals(process)) {
                return row;
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
        Collections.sort(jobs, (Object o1, Object o2) -> {
            if (((Job) o1).getArrivalTime() == ((Job) o2).getArrivalTime()) {
                return 0;
            } else if (((Job) o1).getArrivalTime() < ((Job) o2).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        strategy.process(jobs);
    }
}
