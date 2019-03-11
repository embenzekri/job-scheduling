package com.indev.job.scheduling.scheduler;

import com.indev.job.scheduling.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HPFNonPreemptive extends JobScheduler
{
    @Override
    public void doProcess()
    {
        List<Job> jobs = Utility.deepCopy(this.getJobs());
        int time = jobs.get(0).getArrivalTime();
        
        while (!jobs.isEmpty())
        {
            List<Job> availableJobs = new ArrayList();
            
            for (Job job : jobs)
            {
                if (job.getArrivalTime() <= time)
                {
                    availableJobs.add(job);
                }
            }
            
            Collections.sort(availableJobs, (Object o1, Object o2) -> {
                if (((Job) o1).getPriorityLevel()== ((Job) o2).getPriorityLevel())
                {
                    return 0;
                }
                else if (((Job) o1).getPriorityLevel() < ((Job) o2).getPriorityLevel())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });
            
            Job job = availableJobs.get(0);
            this.getTimeline().add(new Event(job.getProcessName(), time, time + job.getServiceTime()));
            time += job.getServiceTime();
            
            for (int i = 0; i < jobs.size(); i++)
            {
                if (jobs.get(i).getProcessName().equals(job.getProcessName()))
                {
                    jobs.remove(i);
                    break;
                }
            }
        }
        
        for (Job job : this.getJobs())
        {
            job.setWaitingTime(this.getEvent(job).getStartTime() - job.getArrivalTime());
            job.setTurnaroundTime(job.getWaitingTime() + job.getServiceTime());
        }
    }
}
