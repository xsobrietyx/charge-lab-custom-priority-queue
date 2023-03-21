package lab.charge.custom.priority.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by xsobrietyx on 21-March-2023 time 19:00
 */
public class ThrottlingQueueImpl<T> implements ThrottlingQueue<T> {
    private enum ThrottleConstants {
        DEFAULT_THROTTLE_RATE(2);

        private final Integer defaultValue;

        private ThrottleConstants(Integer value) {
            this.defaultValue = value;
        }

        private Integer asInteger() {
            return defaultValue;
        }
    }

    private Integer throttleRate;

    private static class ThrottlingQueueEntry<T> {
        private T value;
        private Integer priority;

        public ThrottlingQueueEntry(T value, Integer priority) {
            this.value = value;
            this.priority = priority;
        }

        public T getValue() {
            return value;
        }

        public Integer getPriority() {
            return priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ThrottlingQueueEntry<?> that = (ThrottlingQueueEntry<?>) o;
            return value.equals(that.value) && priority.equals(that.priority);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, priority);
        }
    }

    private final Map<ThrottlingQueueEntry<T>, Integer> throttleCheck;
    private final Deque<ThrottlingQueueEntry<T>> innerState;

    public ThrottlingQueueImpl(int capacity) {
        this(new HashMap<ThrottlingQueueEntry<T>, Integer>(),
                new ArrayDeque<ThrottlingQueueEntry<T>>(capacity),
                ThrottleConstants.DEFAULT_THROTTLE_RATE.asInteger());
    }

    public ThrottlingQueueImpl(Map<ThrottlingQueueEntry<T>, Integer> throttleCheck,
                               Deque<ThrottlingQueueEntry<T>> innerState,
                               Integer throttleRate) {
        this.throttleCheck = throttleCheck;
        this.innerState = innerState;
        this.throttleRate = throttleRate;
    }

    public boolean enqueue(T value, Integer priority) {
        return innerState.offer(new ThrottlingQueueEntry<T>(value, priority));
    }

    public T dequeue() {
        return null;
    }

    public void setThrottleRate(int rate) {
        this.throttleRate = rate;
    }
}
