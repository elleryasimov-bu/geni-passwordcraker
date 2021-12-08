package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.utils.Range;
import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.workers.Worker;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;
import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Md5DecryptingExecutor {
    private WorkerPool workerPool;
    public ConcurrentHashMap<Integer, BlockingQueue<String>> queueCatalog = new ConcurrentHashMap<>();
    private AtomicInteger seqNum = new AtomicInteger();

    public Md5DecryptingExecutor(WorkerPool workerPool) {
        this.workerPool = workerPool;
    }

    public String serve(String encryptedString) {
        int majorId = seqNum.incrementAndGet();
        queueCatalog.put(majorId, new LinkedBlockingQueue<String>());

        List<WorkerRangePair> workerRangePairs = workerPool.getWorkerRangePairs();

        for (WorkerRangePair pair: workerRangePairs){
            Worker worker = pair.worker;
            Range range = pair.defaultRange;
            System.out.println("Range = " + range.leftBound.toString() + " " + range.rightBound.toString());
            Request newRequest = new Request(majorId, encryptedString, range.leftBound, range.rightBound);
            ManagementUtil.foregroundThreadPool.submit(() -> {
                Worker currWorker = worker;
                while (!currWorker.asyncAssign(newRequest)) {
                    currWorker = currWorker.getSubsituteWorker();
                }
            });
            //worker.asyncAssign(new Request(majorId, encryptedString, range.leftBound, range.rightBound));
        }

        BlockingQueue<String> queue = queueCatalog.get(majorId);
        System.out.println("Receiving majorID = " + majorId);

        int n = 0;

        while (n < workerRangePairs.size()) {
            String result = null;
            try {
                result = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!result.isEmpty()) {
                System.out.println("Receiving " + result);
                queueCatalog.remove(majorId);
                return result;
            }
            n ++;
        }

        queueCatalog.remove(majorId);
        return "";
    }

}
