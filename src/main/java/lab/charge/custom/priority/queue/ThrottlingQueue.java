package lab.charge.custom.priority.queue;

import lab.charge.custom.priority.queue.exception.ThrottleQueueException;

import java.util.Deque;

/**
 * Created by xsobrietyx on 21-March-2023 time 18:58
 */
public interface ThrottlingQueue<T> {
    boolean enqueue(T value, Integer priority) throws ThrottleQueueException;
    T dequeue() throws ThrottleQueueException;
    void setThrottleRate(int rate);
    Deque<ThrottlingQueueImpl.ThrottlingQueueEntry<T>> getInnerState();
}
