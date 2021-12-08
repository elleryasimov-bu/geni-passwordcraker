package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Md5DecryptingExecutor {
    private WorkerPool workerPool;
    private ConcurrentHashMap<Integer, BlockingQueue<String>> queueCatalog;
    private AtomicInteger seqNum;

    public Md5DecryptingExecutor(WorkerPool workerPool) {
        this.workerPool = workerPool;
    }

    public String assign(String encryptedString) {
        int majorId = seqNum.incrementAndGet();
        queueCatalog.put(majorId, new LinkedBlockingQueue<String>());

        List<WorkerRangePair> workerRangePairs = workerPool.getWorkerRangePairs();

        for (){
            worker.asyncAssign(new Request(majorId, encryptedString, range.leftBound, range.rightBound));
        }

        BlockingQueue<String> queue = queueCatalog.get(majorId);

        int n = 0;

        while (n < workerRangePairs.size()) {

            String result = queue.poll();
            if (!result.isEmpty()) {
                queueCatalog.remove(majorId);
                return result;
            }
        }

        queueCatalog.remove(majorId);
        return "";
    }

}
