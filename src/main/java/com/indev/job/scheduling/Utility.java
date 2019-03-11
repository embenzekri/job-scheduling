package com.indev.job.scheduling;

import com.indev.job.scheduling.scheduler.Job;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utility {
    public static DecimalFormat DECIMAL_FORMATER = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    public static List<Job> deepCopy(List<Job> oldList) {
        List<Job> newList = new ArrayList();

        for (Job job : oldList) {
            newList.add(new Job(job.getProcessName(), job.getArrivalTime(), job.getServiceTime(), job.getPriorityLevel()));
        }

        return newList;
    }
}
