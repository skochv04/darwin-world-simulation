package oop;

import oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private List<Simulation> simulations;
    private List<Thread> threads = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
    }
    public void runSync() throws InterruptedException {
        for (Simulation simulation: simulations){
            simulation.run();
        }
    }
    public void runAsync() throws InterruptedException {
        for (Simulation simulation: simulations){
            threads.add(new Thread(simulation));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            };
        }
        catch (InterruptedException e){
            executorService.shutdownNow();

        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation simulation: simulations){
            executorService.submit(simulation);
        }
    }

    public void addSimulation(Simulation simulation){
        simulations.add(simulation);
        Thread thread = new Thread(simulation);
        threads.add(thread);
        thread.start();
    }

}
