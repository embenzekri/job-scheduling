package com.indev.job.scheduling.scheduler;

import com.indev.job.scheduling.Utility;

import java.util.List;

public class RoundRobin extends JobScheduler {
    @Override
    public void doProcess() {
        List<Job> jobs = Utility.deepCopy(this.getJobs());
        int time = jobs.get(0).getArrivalTime();
        int timeQuantum = this.getTimeQuantum();

        while (!jobs.isEmpty()) {
            Job job = jobs.get(0);
            int bt = (job.getServiceTime() < timeQuantum ? job.getServiceTime() : timeQuantum);

            this.getTimeline().add(new Event(job.getProcessName(), time, time + bt));
            time += bt;
            jobs.remove(0);

            if (job.getServiceTime() > timeQuantum) {
                job.setServiceTime(job.getServiceTime() - timeQuantum);

                for (int i = 0; i < jobs.size(); i++) {
                    if (jobs.get(i).getArrivalTime() > time) {
                        jobs.add(i, job);
                        break;
                    } else if (i == jobs.size() - 1) {
                        jobs.add(job);
                        break;
                    }
                }
            }
        }
        for(Job job: getJobs()) {
            Event lastEvent = getTimeline().stream().filter(event -> event.getProcessName().equals(job.getProcessName())).reduce((first, second) -> second).orElse(null);
            updateResponseTime(lastEvent.getFinishTime(),job.getProcessName());
        }

        adjustWTAndTAT();
    }

}
