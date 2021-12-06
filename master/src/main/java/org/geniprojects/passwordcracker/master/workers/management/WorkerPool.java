package org.geniprojects.passwordcracker.master.workers.management;

import org.geniprojects.passwordcracker.master.workers.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkerPool {
    private List<Worker> workers;

    public WorkerPool() {
        workers = new ArrayList<>();
    }

    public void loadWorkersFromJsonFile(String path) {


    }


    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public Worker getWorker() {
        return workers.get(0);
    }

    public List<Worker> getWorkers(int num) {
        return null;
    }
}
