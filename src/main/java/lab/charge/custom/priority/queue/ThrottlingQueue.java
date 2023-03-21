package lab.charge.custom.priority.queue;

/**
 * Created by xsobrietyx on 21-March-2023 time 18:58
 */
public interface ThrottlingQueue<T> {
    boolean enqueue(T value, Integer priority);
    T dequeue();
    void setThrottleRate(int rate);
}
