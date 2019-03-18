package com.indev.job.scheduling.simulation;

import com.indev.job.scheduling.scheduler.Job;
import com.indev.job.scheduling.scheduler.JobScheduler;

import java.util.Random;

import static com.indev.job.scheduling.Utility.DECIMAL_FORMATER;

public class Simulation {

    private JobScheduler schedulerAlgorithm;
    private SchedulerResultPrinter schedulerResultPrinter = new SchedulerResultPrinter();

    public Simulation(JobScheduler schedulerAlgorithm) {
        this.schedulerAlgorithm = schedulerAlgorithm;
    }


    public RunResult simulate(int runCount) {
        System.out.println("Start simulation for " + schedulerAlgorithm.toString());
        double averageWaitingTimeSum = 0.0;
        double averageTurnAroundTimeSum = 0.0;
        double averageResponseTimeSum = 0.0;

        for (int i = 0; i < runCount; i++) {
            RunResult run = singleRun();
            averageWaitingTimeSum += run.getAverageWaitingTime();
            averageTurnAroundTimeSum += run.getAverageTurnAroundTime();
            averageResponseTimeSum += run.getAverageResponseTime();
        }

        double globalAverageWaitingTime = averageWaitingTimeSum / 5;
        double globalAverageTurnAroundTime = averageTurnAroundTimeSum / 5;
        double globalAverageResponseTime = averageResponseTimeSum / 5;

        System.out.println("\nStatistics of 5 runs");
        System.out.println("Average WT for " + runCount + " runs : " + DECIMAL_FORMATER.format(globalAverageWaitingTime));
        System.out.println("Average TAT for " + runCount + " runs: " + DECIMAL_FORMATER.format(globalAverageTurnAroundTime));
        System.out.println("Average RT for " + runCount + " runs: " + DECIMAL_FORMATER.format(globalAverageResponseTime));
        System.out.println("End of simulation for " + schedulerAlgorithm.toString());
        return new RunResult(globalAverageWaitingTime, globalAverageTurnAroundTime, globalAverageResponseTime);
    }

    private RunResult singleRun() {
        schedulerAlgorithm.clearForNextRun();
        int jobsCount = 10;
        for (int i = 0; i < jobsCount; i++) {
            Job job = generateJob("P" + i);
            schedulerAlgorithm.add(job);
        }
        schedulerAlgorithm.process();
        schedulerResultPrinter.printSchedulerResult(schedulerAlgorithm);
        return new RunResult(schedulerAlgorithm.getAverageWaitingTime(), schedulerAlgorithm.getAverageTurnAroundTime(), schedulerAlgorithm.getAverageResponseTime());
    }

    private Job generateJob(String processId) {
        int arrivalTime = new Random().nextInt(5) + 1;
        int serviceTime = new Random().nextInt(10) + 1;
        int priority = new Random().nextInt(5) + 1;

        return createJob(processId, arrivalTime, serviceTime, priority);
    }

    private Job createJob(String processId, int arrivalTime, int serviceTime, int priority) {

        return new Job(processId, arrivalTime, serviceTime, priority);
    }
}
