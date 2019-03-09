package com.indev.job.scheduling;

import java.util.List;

public interface JobSchedulerStrategy {
    List<Event> process(List<Job> jobs);
}
