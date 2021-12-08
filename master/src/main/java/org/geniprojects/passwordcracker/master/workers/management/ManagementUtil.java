package org.geniprojects.passwordcracker.master.workers.management;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagementUtil {
    public static final ExecutorService backgroundThreadPool = Executors.newCachedThreadPool();
    public static final WorkerPool workerPool = new WorkerPool();
}
