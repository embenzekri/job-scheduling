package com.indev.job.scheduling;

import java.util.ArrayList;
import java.util.List;

public class Utility
{
    public static List<Job> deepCopy(List<Job> oldList)
    {
        List<Job> newList = new ArrayList();
        
        for (Job row : oldList)
        {
            newList.add(new Job(row.getProcessName(), row.getArrivalTime(), row.getBurstTime(), row.getPriorityLevel()));
        }
        
        return newList;
    }
}
