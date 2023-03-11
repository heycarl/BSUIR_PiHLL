package grvt.cloud.epam_web.perfomance_counter;

public class CounterLogic {
    private static Integer metric_visitors = 0;
    public static void Increment() {
        metric_visitors++;
    }
    public static Integer getMetric_visitors() {
        return metric_visitors;
    }
}
