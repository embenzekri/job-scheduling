package com.indev.job.scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestJobFirst extends JobScheduler
{
    @Override
    public void process()
    {
        Collections.sort(this.getJobs(), (Object o1, Object o2) -> {
            if (((Job) o1).getArrivalTime() == ((Job) o2).getArrivalTime())
            {
                return 0;
            }
            else if (((Job) o1).getArrivalTime() < ((Job) o2).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });
        
        List<Job> rows = Utility.deepCopy(this.getJobs());
        int time = rows.get(0).getArrivalTime();
        
        while (!rows.isEmpty())
        {
            List<Job> availableRows = new ArrayList();
            
            for (Job row : rows)
            {
                if (row.getArrivalTime() <= time)
                {
                    availableRows.add(row);
                }
            }
            
            Collections.sort(availableRows, (Object o1, Object o2) -> {
                if (((Job) o1).getBurstTime() == ((Job) o2).getBurstTime())
                {
                    return 0;
                }
                else if (((Job) o1).getBurstTime() < ((Job) o2).getBurstTime())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });
            
            Job row = availableRows.get(0);
            this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime()));
            time += row.getBurstTime();
            
            for (int i = 0; i < rows.size(); i++)
            {
                if (rows.get(i).getProcessName().equals(row.getProcessName()))
                {
                    rows.remove(i);
                    break;
                }
            }
        }
        
        for (Job row : this.getJobs())
        {
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
