package grvt.cloud.epam_web.perfomance_counter;

public class CounterLogic {
    private static Integer metric_visitors = 0;
    public static synchronized void Increment() {
        metric_visitors++;
    }
    public static synchronized Integer getMetric_visitors() {
        return metric_visitors;
    }
}
