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
    public void assignRange() throws Exception {
        this.ranges= StringSpliter.splitStrforWorkers(this.workers.size());
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

    public List<WorkerRangePair> getWorkerRangePairs() throws Exception {

        Deque<Integer> errorRangeIndexes= new ArrayDeque<Integer>() ;

        List<WorkerRangePair> newPairs= new ArrayList<WorkerRangePair>();

        int availableWorkerCounter=0;

        // default assign to each worker
        this.assignRange();

        //collect indexes of unavailable workers
        for (int i=0;i<workers.size();i++)
        {

            if( workers.get(i).availability==false)
            {
                errorRangeIndexes.addFirst(i);
            }
            else
            {
                availableWorkerCounter++;
            }

        }
        int extraWorkPerWorker=errorRangeIndexes.size()/availableWorkerCounter;
        int extraWorkRemainder=errorRangeIndexes.size()%availableWorkerCounter;

        //reassign work
        for (int i=0;i<workers.size();i++)
        {
            Worker w = workers.get(i);
            if( w.availability==true)
            {
                ArrayList<Range> r = new ArrayList<Range>();

                //default load
                r.add(ranges.get(i));

                //extra load
                for(int j =0 ;j<extraWorkPerWorker;j++) {
                    int tempIndex = errorRangeIndexes.getFirst();
                    if (tempIndex != i) {
                        r.add(ranges.get(errorRangeIndexes.removeFirst()));
                    }
                    else
                    {
                        extraWorkRemainder++;
                    }
                }

                if(extraWorkRemainder>0)
                {
                    int tempIndex = errorRangeIndexes.getFirst();
                    if (tempIndex != i) {
                        r.add(ranges.get(errorRangeIndexes.removeFirst()));
                        extraWorkRemainder--;
                    }
                }

                //append final WorkerRangePair
                newPairs.add( new WorkerRangePair(w,r));
            }
        }

        return newPairs;
    }

    public void printCurrentRangeAssignment() {

    }
}
