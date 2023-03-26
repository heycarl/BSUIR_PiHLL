package grvt.cloud.epam.perfomance_counter;

public class CounterLogic {
    private static Integer metricVisitors = 0;

    public static void increment() {
        metricVisitors++;
    }

    public static Integer getMetricVisitors() {
        return metricVisitors;
    }
}
