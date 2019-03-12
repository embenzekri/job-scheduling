package com.indev.job.scheduling.simulation;

import com.indev.job.scheduling.scheduler.Event;
import com.indev.job.scheduling.scheduler.Job;
import com.indev.job.scheduling.scheduler.JobScheduler;

import java.util.List;
import java.util.Map;

import static com.indev.job.scheduling.Utility.DECIMAL_FORMATER;


public class SchedulerResultPrinter {
    public void printSchedulerResult(JobScheduler scheduler) {
        System.out.println();
        System.out.println("Process\tAT\tST\tWT\tTAT");

        for (Job job : scheduler.getJobs()) {
            System.out.println(job.getProcessName() + "\t\t" + job.getArrivalTime() + "\t" + job.getServiceTime() + "\t" + job.getWaitingTime() + "\t" + job.getTurnaroundTime());
        }

        System.out.println();

        for (int i = 0; i < scheduler.getTimeline().size(); i++) {
            List<Event> timeline = scheduler.getTimeline();
            System.out.print(timeline.get(i).getStartTime() + "(" + timeline.get(i).getProcessName() + ")");

            if (i == scheduler.getTimeline().size() - 1) {
                System.out.print(timeline.get(i).getFinishTime());
            }
        }

        System.out.println("\n\nAverage WT: " + scheduler.getAverageWaitingTime() + "\nAverage TAT: " + scheduler.getAverageTurnAroundTime());
    }

    public void printSimulationResult(Map<String, RunResult> simulationResults) {
        System.out.println("Algorithm\tWT\tTAT");

        double minWT = Double.MAX_VALUE;
        double minTAT = Double.MAX_VALUE;
        String minWTAlgorithm = "";
        String minTATAlgorithm = "";

        for (String algorithm : simulationResults.keySet()) {
            RunResult runResult = simulationResults.get(algorithm);
            System.out.println(algorithm + "\t\t" + DECIMAL_FORMATER.format(runResult.getAverageWaitingTime()) + "\t" + DECIMAL_FORMATER.format(runResult.getAverageTurnAroundTime()));
            if (runResult.getAverageWaitingTime() < minWT) {
                minWT = runResult.getAverageWaitingTime();
                minWTAlgorithm = algorithm;
            }
            if (runResult.getAverageTurnAroundTime() < minTAT) {
                minTAT = runResult.getAverageTurnAroundTime();
                minTATAlgorithm = algorithm;
            }
        }

        System.out.println();

        System.out.println("\n\nMin Average WT: " + minWTAlgorithm + "(" + DECIMAL_FORMATER.format(minWT) + ")" + "\nMin Average TAT: " + minTATAlgorithm + "(" + DECIMAL_FORMATER.format(minTAT) + ") ");
    }

}
