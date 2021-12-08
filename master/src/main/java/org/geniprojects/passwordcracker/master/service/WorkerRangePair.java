package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.utils.Range;
import org.geniprojects.passwordcracker.master.workers.Worker;

import java.util.ArrayList;

public class WorkerRangePair {
    public Worker worker;
    public ArrayList<Range> defaultRange;
    public WorkerRangePair(Worker w,ArrayList<Range> r)
    {
        this.worker=w;
        this.defaultRange=r;
    }
}
