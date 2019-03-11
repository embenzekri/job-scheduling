package com.indev.job.scheduling.scheduler;

import com.indev.job.scheduling.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundRobin extends JobScheduler
{
    @Override
    public void doProcess()
    {
        List<Job> jobs = Utility.deepCopy(this.getJobs());
        int time = jobs.get(0).getArrivalTime();
        int timeQuantum = this.getTimeQuantum();
        
        while (!jobs.isEmpty())
        {
            Job job = jobs.get(0);
            int bt = (job.getServiceTime() < timeQuantum ? job.getServiceTime() : timeQuantum);
            this.getTimeline().add(new Event(job.getProcessName(), time, time + bt));
            time += bt;
            jobs.remove(0);
            
            if (job.getServiceTime() > timeQuantum)
            {
                job.setServiceTime(job.getServiceTime() - timeQuantum);
                
                for (int i = 0; i < jobs.size(); i++)
                {
                    if (jobs.get(i).getArrivalTime() > time)
                    {
                        jobs.add(i, job);
                        break;
                    }
                    else if (i == jobs.size() - 1)
                    {
                        jobs.add(job);
                        break;
                    }
                }
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
