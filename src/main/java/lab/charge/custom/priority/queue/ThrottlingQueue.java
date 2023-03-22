package lab.charge.custom.priority.queue;

import lab.charge.custom.priority.queue.exception.ThrottleQueueException;

import java.util.Deque;

/**
 * Created by xsobrietyx on 21-March-2023 time 18:58
 */
public interface ThrottlingQueue<T> {
    /**
     * @param value generic value aside of priority
     * @param priority priority used within queue
     * @return true if entry has been added to the queue and false otherwise
     * @throws ThrottleQueueException thrown if queue is full and there is no space there for new entries
     */
    boolean enqueue(T value, Integer priority) throws ThrottleQueueException;

    /**
     * @return value of generic type that was used for this queue
     * @throws ThrottleQueueException thrown if queue is empty
     */
    T dequeue() throws ThrottleQueueException;

    /**
     * @param rate can be specified aside of the default rate which is 2
     */
    void setThrottleRate(int rate);

    /**
     * @return copy of inner deque. Can be used for specific iteration purposes
     */
    Deque<ThrottlingQueueImpl.ThrottlingQueueEntry<T>> getInnerState();
}
