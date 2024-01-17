package agh.ics.oop.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements Runnable{
    private List<Simulation> simulations;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() throws InterruptedException {
        for (Simulation simulation : simulations){
            simulation.startSimulation();
        }
    }
    public void runAsync() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
        awaitSimulationsEnd(threads);
    }

    public void runAsyncInThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations) {
            executorService.submit(simulation);
        }
        executorService.shutdown();

        try{
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void awaitSimulationsEnd(List<Thread> threads) throws InterruptedException {
        for (Thread simulationThread : threads) {
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void run() {
        List<Thread> simulationThreads = new ArrayList<>();

        for (Simulation simulation : simulations) {
            simulationThreads.add(new Thread(simulation));
        }
        for (Thread simulationThread : simulationThreads) {
            simulationThread.start();
        }

        try {
            awaitSimulationsEnd(simulationThreads);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
