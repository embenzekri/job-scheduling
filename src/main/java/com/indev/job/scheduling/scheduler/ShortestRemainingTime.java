package com.indev.job.scheduling.scheduler;

import com.indev.job.scheduling.Utility;

import java.util.ArrayList;
import java.util.List;

public class ShortestRemainingTime extends JobScheduler {

    @Override
    public void doProcess() {
        List<Job> jobs = Utility.deepCopy(this.getJobs());
        int time = 0;

        while (!jobs.isEmpty()) {
            List<Job> availableJobs = new ArrayList();

            for (Job job : jobs) {
                if (job.getArrivalTime() <= time) {
                    availableJobs.add(job);
                }
            }

            if (availableJobs.isEmpty()) {
                time++;
                increaseIdleTime(1);
                continue;
            }
            jobSort.sortByServiceTime(availableJobs);

            Job job = availableJobs.get(0);
            updateResponseTime(time, job.getProcessName());
            this.getTimeline().add(new Event(job.getProcessName(), time, ++time));
            job.setServiceTime(job.getServiceTime() - 1);
            if (job.getServiceTime() == 0) {
                for (int i = 0; i < jobs.size(); i++) {
                    if (jobs.get(i).getProcessName().equals(job.getProcessName())) {
                        jobs.remove(i);
                        break;
                    }
                }
            }
        }

        for (int i = this.getTimeline().size() - 1; i > 0; i--) {
            List<Event> timeline = this.getTimeline();

            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName())) {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());

                timeline.remove(i);
            }
        }

        adjustWTAndTAT();
    }


}
