package org.geniprojects.passwordcracker.master.workers.management;

import org.geniprojects.passwordcracker.master.service.StringSpliter;
import org.geniprojects.passwordcracker.master.service.WorkerRangePair;
import org.geniprojects.passwordcracker.master.utils.Range;
import org.geniprojects.passwordcracker.master.workers.Worker;

import java.sql.Wrapper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class WorkerPool {
    private ArrayList<Worker> workers;
    private ArrayList<Range> ranges;
    private boolean rangeAssigned;

    public WorkerPool() {
        workers = new ArrayList<>();
    }

    public void loadWorkersFromJsonFile(String path) {


    }

    // Caution: this method will reassign ranges that workers responsible for, which may cause
    // performance degration, because of cache miss. All currently unavailable workers will be
    // cleared.
    public void assignRange() {
        this.ranges= StringSpliter.splitStrforWorkers(this.workers.size());
    }


    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public synchronized void addWorker(String ipAddress, int port) {
        Worker newWorker = new Worker(ipAddress, port);
        newWorker.availability.set(true);
        if (workers.size() != 0) {
            newWorker.setPreviousWorker(workers.get(workers.size() - 1));
            workers.get(0).setPreviousWorker(newWorker);
        }
        workers.add(newWorker);
    }

    public Worker getWorker() {
        return workers.get(0);
    }

    public List<Worker> getWorkers(int num) {
        return null;
    }

    public List<WorkerRangePair> getWorkerRangePairs() {

        if (!hasAvailableWorkers()) return null;

        //Deque<Integer> errorRangeIndexes= new ArrayDeque<Integer>() ;

        List<WorkerRangePair> newPairs= new ArrayList<WorkerRangePair>();

        int availableWorkerCounter=0;

        // default assign to each worker
        this.assignRange();

        Worker lastAvailableWorker;
        for (int i = 0; i < ranges.size(); i ++) {
            Worker assignedWorker = workers.get(i);
            while (!assignedWorker.availability.get()) assignedWorker = assignedWorker.getSubsituteWorker();
            newPairs.add(new WorkerRangePair(assignedWorker, ranges.get(i)));
        }

        return newPairs;

//        //collect indexes of unavailable workers
//        for (int i=0;i<workers.size();i++)
//        {
//
//            if(!workers.get(i).availability.get())
//            {
//                errorRangeIndexes.addFirst(i);
//            }
//            else
//            {
//                availableWorkerCounter++;
//            }
//
//        }
//        int extraWorkPerWorker=errorRangeIndexes.size()/availableWorkerCounter;
//        int extraWorkRemainder=errorRangeIndexes.size()%availableWorkerCounter;
//
//        //reassign work
//        for (int i=0;i<workers.size();i++)
//        {
//            Worker w = workers.get(i);
//            if(w.availability.get())
//            {
//                ArrayList<Range> r = new ArrayList<Range>();
//
//                //default load
//                r.add(ranges.get(i));
//
//                //extra load
//                for(int j =0 ;j<extraWorkPerWorker;j++) {
//                    int tempIndex = errorRangeIndexes.getFirst();
//                    if (tempIndex != i) {
//                        r.add(ranges.get(errorRangeIndexes.removeFirst()));
//                    }
//                    else
//                    {
//                        extraWorkRemainder++;
//                    }
//                }
//
//                if(extraWorkRemainder>0)
//                {
//                    int tempIndex = errorRangeIndexes.getFirst();
//                    if (tempIndex != i) {
//                        r.add(ranges.get(errorRangeIndexes.removeFirst()));
//                        extraWorkRemainder--;
//                    }
//                }
//
//                //append final WorkerRangePair
//                newPairs.add( new WorkerRangePair(w,r));
//            }
//        }

    }

    public boolean hasAvailableWorkers() {
        for (Worker worker: workers) {
            if (worker.availability.get()) return true;
        }
        return false;
    }

    public void printCurrentRangeAssignment() {

    }
}
