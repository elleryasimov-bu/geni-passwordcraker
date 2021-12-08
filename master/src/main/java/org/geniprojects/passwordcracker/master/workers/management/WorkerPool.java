package org.geniprojects.passwordcracker.master.workers.management;

import org.geniprojects.passwordcracker.master.service.WorkerRangePair;
import org.geniprojects.passwordcracker.master.utils.Range;
import org.geniprojects.passwordcracker.master.workers.Worker;

import java.util.ArrayList;
import java.util.List;


public class WorkerPool {
    private List<Worker> workers;
    private List<Range> ranges;
    private boolean rangeAssigned;

    public WorkerPool() {
        workers = new ArrayList<>();
    }

    public void loadWorkersFromJsonFile(String path) {


    }

    // Caution: this method will reassign ranges that workers responsible for, which may cause
    // performance degration, because of cache miss. All currently unavailable workers will be
    // cleared.
    public boolean assignRange() {
        return false;
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

    public List<WorkerRangePair> getWorkerRangePairs() {
        return null;
    }

    public void printCurrentRangeAssignment() {

    }
}
