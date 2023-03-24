package grvt.cloud.epam_web.perfomance_counter;

import grvt.cloud.epam_web.controllers.TriangleController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Counter extends Thread{
    private static final Logger logger = LogManager.getLogger();
    @Override
    public synchronized void run() {
        CounterLogic.Increment();
    }
}
