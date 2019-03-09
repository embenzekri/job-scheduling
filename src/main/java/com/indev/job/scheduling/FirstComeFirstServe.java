package com.indev.job.scheduling;

import java.util.ArrayList;
import java.util.List;

public class FirstComeFirstServe implements JobSchedulerStrategy {
    @Override
    public List<Event> process(List<Job> jobs) {
        List<Event> timeline = new ArrayList<>();
        List<JobRunReport> reports = new ArrayList<>();
        int startTime = 0;
        int timeSlices = 0;
        for (Job job : jobs) {
            JobRunReport runReport = job.runFor(startTime, );
            reports.add(runReport);
            startTime = runReport.getCompletionTime();
        }

        return timeline;
    }
}
