package com.indev.job.scheduling;

import com.indev.job.scheduling.scheduler.*;
import com.indev.job.scheduling.simulation.RunResult;
import com.indev.job.scheduling.simulation.SchedulerResultPrinter;
import com.indev.job.scheduling.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        int simulationCount = 5;

        RunResult fcfsResult = new Simulation(new FirstComeFirstServed()).simulate(simulationCount);
        RunResult shortedJobFirstResult = new Simulation(new ShortestJobFirst()).simulate(simulationCount);
        RunResult shortedRemTimeResult = new Simulation(new ShortestRemainingTime()).simulate(simulationCount);
        RunResult roundRobinResult = new Simulation(new RoundRobin()).simulate(simulationCount);
        RunResult hpfNonPreemptiveResult = new Simulation(new HPFNonPreemptive()).simulate(simulationCount);
        RunResult hpfPreemptiveResult = new Simulation(new HPFPreemptive()).simulate(simulationCount);

        Map<String, RunResult> simulationResults = new HashMap<>();
        simulationResults.put("FirstComeFirstServed", fcfsResult);
        simulationResults.put("ShortestJobFirst", shortedJobFirstResult);
        simulationResults.put("ShortestRemainingTime", shortedRemTimeResult);
        simulationResults.put("RoundRobin", roundRobinResult);
        simulationResults.put("HPFNonPreemptive", hpfNonPreemptiveResult);
        simulationResults.put("HPFPreemptive", hpfPreemptiveResult);

        new SchedulerResultPrinter().printSimulationResult(simulationResults);
    }
}
