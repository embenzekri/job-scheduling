package com.indev.job.scheduling.scheduler;

import java.util.*;

public abstract class JobScheduler {
    private List<Job> jobs;
    private List<Event> timeline;
    private int timeQuantum;
    private int maxTimeQuantum;
    private int idleTime;
    protected JobSort jobSort;

    public JobScheduler() {
        jobs = new ArrayList();
        timeline = new ArrayList();
        timeQuantum = 1;
        maxTimeQuantum = 10;
        jobSort = new JobSort();
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

    public int getMaxTimeQuantum() {
        return maxTimeQuantum;
    }

    public void setMaxTimeQuantum(int maxTimeQuantum) {
        this.maxTimeQuantum = maxTimeQuantum;
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

    public double getAverageResponseTime() {
        double avg = 0.0;

        for (Job job : jobs) {
            avg += job.getResponseTime();
        }

        return avg / jobs.size();
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public List<Event> getTimeline() {
        return timeline;
    }

    public void process() {
        jobSort.sortByArrivalTime(this.getJobs());

        doProcess();
    }

    public abstract void doProcess();


    protected void adjustWTAndTAT() {
        Map map = new HashMap();
        for (Job job : this.getJobs()) {
            map.clear();

            for (Event event : this.getTimeline()) {
                if (event.getProcessName().equals(job.getProcessName())) {
                    if (map.containsKey(event.getProcessName())) {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        job.setWaitingTime(job.getWaitingTime() + w);
                    } else {
                        job.setWaitingTime(event.getStartTime() - job.getArrivalTime());
                    }

                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }
            if (job.getWaitingTime() < 0) {
                job.setWaitingTime(0);
            }
            job.setTurnaroundTime(job.getWaitingTime() + job.getServiceTime());
        }
    }


    protected Optional<Job> getJob(String processName) {
        for (Job job : this.getJobs()) {
            if (processName.equals(job.getProcessName())) {
                return Optional.of(job);
            }
        }
        return Optional.empty();
    }


    protected void updateResponseTime(int responseTime, String jobName) {
        Optional<Job> job = getJob(jobName);
        if (job.isPresent() && job.get().getResponseTime() == 0) {
            job.get().setResponseTime(responseTime);
        }
    }

    protected void increaseIdleTime(int idleTime) {
        this.idleTime += idleTime;
    }

    public int getIdleTime() {
        return idleTime;
    }
}
