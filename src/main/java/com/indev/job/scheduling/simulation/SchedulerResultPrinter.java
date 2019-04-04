package com.indev.job.scheduling.simulation;

import com.indev.job.scheduling.scheduler.*;

import java.util.List;
import java.util.Map;

import static com.indev.job.scheduling.Utility.DECIMAL_FORMATER;


public class SchedulerResultPrinter {
    public void printSchedulerResult(JobScheduler scheduler) {
        System.out.println();
        if (scheduler instanceof HPFNonPreemptive || scheduler instanceof HPFPreemptive) {
            printWithPriority(scheduler, true);
        } else {
            printWithPriority(scheduler, false);
        }

        System.out.println();

        for (int i = 0; i < scheduler.getTimeline().size(); i++) {
            List<Event> timeline = scheduler.getTimeline();
            System.out.print(timeline.get(i).getStartTime() + "(" + timeline.get(i).getProcessName() + ")");

            if (i == scheduler.getTimeline().size() - 1) {
                System.out.print(timeline.get(i).getFinishTime());
            }
        }

        System.out.println("\n\nAverage WT: " + DECIMAL_FORMATER.format(scheduler.getAverageWaitingTime()));
        System.out.println("Average RT: " + DECIMAL_FORMATER.format(scheduler.getAverageResponseTime()));
        System.out.println("Average TAT: " + DECIMAL_FORMATER.format(scheduler.getAverageTurnAroundTime()));
    }

    private void printWithPriority(JobScheduler scheduler, boolean printPriority) {
        System.out.print(String.format("%-30s%-10s%-10s", "Process", "AT", "ST"));
        if (printPriority) {
            System.out.print(String.format("%-10s", "PRT"));
        }
        System.out.print(String.format("%-10s%-10s%-10s\n", "WT", "RT", "TAT"));

        for (Job job : scheduler.getJobs()) {
            System.out.print(String.format("%-30s%-10s%-10s", job.getProcessName(), job.getArrivalTime(), job.getServiceTime()));
            if (printPriority) {

                System.out.print(String.format("%-10s", job.getPriorityLevel()));
            }
            System.out.print(String.format("%-10s%-10s%-10s\n", job.getWaitingTime(), job.getResponseTime(), job.getTurnaroundTime()));
        }
    }

    public void printSimulationResult(Map<String, RunResult> simulationResults, int simulationCount) {
        System.out.println(String.format("\n%-30s%-10s%-10s%-10s\n", "Algorithm", "WT", "RT", "TAT"));

        double minWT = Double.MAX_VALUE;
        double minTAT = Double.MAX_VALUE;
        double minRT = Double.MAX_VALUE;
        String minWTAlgorithm = "";
        String minTATAlgorithm = "";
        String minRTAlgorithm = "";

        for (String algorithm : simulationResults.keySet()) {
            RunResult runResult = simulationResults.get(algorithm);

            System.out.print(String.format("%-30s", algorithm));
            System.out.print(String.format("%-10s", DECIMAL_FORMATER.format(runResult.getAverageWaitingTime())));
            System.out.print(String.format("%-10s", DECIMAL_FORMATER.format(runResult.getAverageResponseTime())));
            System.out.print(String.format("%-10s", DECIMAL_FORMATER.format(runResult.getAverageTurnAroundTime())));
            System.out.println();
            if (runResult.getAverageWaitingTime() < minWT) {
                minWT = runResult.getAverageWaitingTime();
                minWTAlgorithm = algorithm;
            }
            if (runResult.getAverageTurnAroundTime() < minTAT) {
                minTAT = runResult.getAverageTurnAroundTime();
                minTATAlgorithm = algorithm;
            }
            if (runResult.getAverageResponseTime() < minRT) {
                minRT = runResult.getAverageResponseTime();
                minRTAlgorithm = algorithm;
            }
        }

        System.out.println();

        System.out.println("\n\nMin Average WT for " + simulationCount + " simulations :\t" + minWTAlgorithm + "(" + DECIMAL_FORMATER.format(minWT) + ")");
        System.out.println("Min Average RT for " + simulationCount + " simulations :\t" + minRTAlgorithm + "(" + DECIMAL_FORMATER.format(minRT) + ") ");
        System.out.println("Min Average TAT for " + simulationCount + " simulations :\t" + minTATAlgorithm + "(" + DECIMAL_FORMATER.format(minTAT) + ") ");
    }

}
