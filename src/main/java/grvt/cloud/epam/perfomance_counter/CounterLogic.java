package grvt.cloud.epam.perfomance_counter;

public class CounterLogic {
    private static Integer metric_visitors = 0;
    public static void Increment() {
        metric_visitors++;
    }
    public static Integer getMetricVisitors() {
        return metric_visitors;
    }
}
