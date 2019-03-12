package com.indev.job.scheduling.scheduler;

import java.util.List;

public class FirstComeFirstServed extends JobScheduler {
    @Override
    public void doProcess() {

        List<Event> timeline = this.getTimeline();

        for (Job job : this.getJobs()) {
            if (timeline.isEmpty()) {
                timeline.add(new Event(job.getProcessName(), job.getArrivalTime(), job.getArrivalTime() + job.getServiceTime()));
            } else {
                Event event = timeline.get(timeline.size() - 1);
                timeline.add(new Event(job.getProcessName(), event.getFinishTime(), event.getFinishTime() + job.getServiceTime()));
            }
        }

        for (Job job : this.getJobs()) {
            job.setWaitingTime(this.getEvent(job).getStartTime() - job.getArrivalTime());
            job.setTurnaroundTime(job.getWaitingTime() + job.getServiceTime());
        }
    }
}
