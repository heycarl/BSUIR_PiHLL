package grvt.cloud.epam.perfomance_counter;

public class Counter extends Thread{
    @Override
    public synchronized void run() {
        CounterLogic.Increment();
    }
}
