package com.indev.job.scheduling.scheduler;

import java.util.Collections;
import java.util.List;

public class JobSort {

    public void sortByServiceTime(List<Job> availableJobs) {
        Collections.sort(availableJobs, (Object o1, Object o2) -> {
            if (((Job) o1).getServiceTime() == ((Job) o2).getServiceTime()) {
                return 0;
            } else if (((Job) o1).getServiceTime() < ((Job) o2).getServiceTime()) {
                return -1;
            } else {
                return 1;
            }
        });
    }

    public void sortByPriority(List<Job> availableJobs) {
        Collections.sort(availableJobs, (Object o1, Object o2) -> {
            if (((Job) o1).getPriorityLevel() == ((Job) o2).getPriorityLevel()) {
                return 0;
            } else if (((Job) o1).getPriorityLevel() < ((Job) o2).getPriorityLevel()) {
                return -1;
            } else {
                return 1;
            }
        });
    }
}
