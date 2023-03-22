package lab.charge.custom.priority.queue;

import lab.charge.custom.priority.queue.exception.ThrottleQueueException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Created by xsobrietyx on 21-March-2023 time 19:00
 */
public final class ThrottlingQueueImpl<T> implements ThrottlingQueue<T> {
    private final Object key = new Object();

    enum ThrottleConstants {
        DEFAULT_THROTTLE_RATE(2),
        DEFAULT_PRIORITY(1),

        EXCEPTION_QUEUE_EMPTY("Queue is empty"),

        EXCEPTION_QUEUE_FULL("Queue is full");

        private Integer defaultValue;
        private String exceptionMessage;

        ThrottleConstants(Integer value) {
            this.defaultValue = value;
        }

        ThrottleConstants(String value) {
            this.exceptionMessage = value;
        }

        Integer asInteger() {
            return defaultValue;
        }

        String asString() {
            return exceptionMessage;
        }
    }

    private int throttleRate;
    private int currentPriority;

    private final int maxCapacity;
    private boolean skipNext;

    static class ThrottlingQueueEntry<T> {
        private final T value;
        private final Integer priority;

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

        @Override
        public String toString() {
            return "ThrottlingQueueEntry{" +
                    "value=" + value +
                    ", priority=" + priority +
                    '}';
        }
    }

    private final Map<T, Integer> throttleCheck;
    private final ArrayDeque<ThrottlingQueueEntry<T>> innerState;

    public ThrottlingQueueImpl(int capacity) {
        this(new HashMap<>(),
                new ArrayDeque<>(capacity),
                ThrottleConstants.DEFAULT_THROTTLE_RATE.asInteger(),
                ThrottleConstants.DEFAULT_PRIORITY.asInteger(),
                false,
                capacity);
    }

    private ThrottlingQueueImpl(Map<T, Integer> throttleCheck,
                                ArrayDeque<ThrottlingQueueEntry<T>> innerState,
                                Integer throttleRate,
                                Integer currentPriority,
                                boolean skipNext,
                                int maxCapacity) {
        this.throttleCheck = throttleCheck;
        this.innerState = innerState;
        this.throttleRate = throttleRate;
        this.currentPriority = currentPriority;
        this.skipNext = skipNext;
        this.maxCapacity = maxCapacity;
    }

    public boolean enqueue(T value, Integer priority) throws ThrottleQueueException {
        synchronized (key) {
            if (innerState.size() == maxCapacity) {
                throw new ThrottleQueueException(ThrottleConstants.EXCEPTION_QUEUE_FULL.asString());
            }
            throttleCheck.computeIfPresent(value, (k, integer) -> {
                if (Objects.equals(throttleCheck.get(k), throttleRate)) {
                    skipNext = true;
                    return integer - 1;
                }
                return integer - 1;
            });
            return innerState.offer(new ThrottlingQueueEntry<>(value, priority));
        }
    }

    public T dequeue() throws ThrottleQueueException {
        synchronized (key) {
            if (innerState.size() == 0) {
                throw new ThrottleQueueException(ThrottleConstants.EXCEPTION_QUEUE_EMPTY.asString());
            }
            ThrottlingQueueEntry<T> currentEl = null;
            for (ThrottlingQueueEntry<T> entry : innerState) {
                throttleCheck.putIfAbsent(entry.getValue(), 0);
                Integer throttleState = throttleCheck.get(entry.getValue());
                Set<Integer> priorities = innerState.stream()
                        .map(ThrottlingQueueEntry::getPriority)
                        .collect(Collectors.toSet());
                if (!priorities.contains(currentPriority)) currentPriority++;
                if (priorities.contains(currentPriority - 1) && !skipNext) currentPriority--;
                if (entry.getPriority() == currentPriority) {
                    if (skipNext) currentPriority--;
                    if (throttleState < throttleRate) {
                        throttleCheck.put(entry.getValue(), throttleState + 1);
                        currentEl = entry;
                        skipNext = false;
                        break;
                    }
                }
            }

            if (Objects.equals(throttleCheck.get(requireNonNull(currentEl).getValue()),
                    throttleRate)) {
                currentPriority++;
            }
            innerState.remove(requireNonNull(currentEl));
            return currentEl.getValue();
        }
    }

    public void setThrottleRate(int rate) {
        synchronized (key) {
            this.throttleRate = rate;
        }
    }

    public Deque<ThrottlingQueueEntry<T>> getInnerState() {
        synchronized (key) {
            return innerState.clone();
        }
    }
}
