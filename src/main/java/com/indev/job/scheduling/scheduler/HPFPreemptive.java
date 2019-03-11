package com.indev.job.scheduling.scheduler;

import com.indev.job.scheduling.Utility;

import java.util.*;

public class HPFPreemptive extends JobScheduler
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
            this.getTimeline().add(new Event(job.getProcessName(), time, ++time));
            job.setServiceTime(job.getServiceTime() - 1);
            
            if (job.getServiceTime() == 0)
            {
                for (int i = 0; i < jobs.size(); i++)
                {
                    if (jobs.get(i).getProcessName().equals(job.getProcessName()))
                    {
                        jobs.remove(i);
                        break;
                    }
                }
            }
        }
        
        for (int i = this.getTimeline().size() - 1; i > 0; i--)
        {
            List<Event> timeline = this.getTimeline();
            
            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName()))
            {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }
        
        Map map = new HashMap();
        
        for (Job job : this.getJobs())
        {
            map.clear();
            
            for (Event event : this.getTimeline())
            {
                if (event.getProcessName().equals(job.getProcessName()))
                {
                    if (map.containsKey(event.getProcessName()))
                    {
                        int w = event.getStartTime() - (int) map.get(event.getProcessName());
                        job.setWaitingTime(job.getWaitingTime() + w);
                    }
                    else
                    {
                        job.setWaitingTime(event.getStartTime() - job.getArrivalTime());
                    }
                    
                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }
            
            job.setTurnaroundTime(job.getWaitingTime() + job.getServiceTime());
        }
    }
}
