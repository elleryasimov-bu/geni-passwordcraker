package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;

public class ServiceUtil {
    public static final Md5DecryptingExecutor md5DecryptExecutor = new Md5DecryptingExecutor(ManagementUtil.workerPool);
}