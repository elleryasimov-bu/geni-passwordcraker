package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;
import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

public class Md5DecryptingController {
    private WorkerPool workerPool;

    public static String serve(String encryptedString) {
        try {
            return ManagementUtil.workerPool.getWorker().assign(new Request(encryptedString, "AAAAA", "ZZZZZ")).deCryptedString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
