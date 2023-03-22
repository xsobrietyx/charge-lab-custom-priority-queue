package lab.charge.custom.priority.queue;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lab.charge.custom.priority.queue.ThrottlingQueueImpl.ThrottleConstants;
import lab.charge.custom.priority.queue.exception.ThrottleQueueException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Unit test for simple App.
 */
public class ThrottlingQueueTest extends TestCase {
    private static final String EXCEPTION_PREFIX = "[ThrottleQueue exception]:";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ThrottlingQueueTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ThrottlingQueueTest.class);
    }

    public void testExampleOne() throws ThrottleQueueException {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        testQueue.enqueue(4, 4);
        testQueue.enqueue(1, 1);
        testQueue.enqueue(3, 3);
        testQueue.enqueue(2, 2);
        testQueue.enqueue(1, 1);
        testQueue.enqueue(2, 2);

        List<Integer> results = new ArrayList<>();

        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        testQueue.enqueue(1, 1);
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());

        assertEquals(asList(1, 1, 2, 1, 2, 3, 4), results);
    }

    public void testExampleTwo() throws ThrottleQueueException {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        testQueue.enqueue(4, 4);
        testQueue.enqueue(1, 1);
        testQueue.enqueue(3, 3);
        testQueue.enqueue(2, 2);
        testQueue.enqueue(2, 2);

        List<Integer> results = new ArrayList<>();

        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        testQueue.enqueue(1, 1);
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());

        assertEquals(asList(1, 2, 1, 2, 3, 4), results);
    }

    public void testSetThrottling() throws ThrottleQueueException {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        testQueue.setThrottleRate(3);
        testQueue.enqueue(4, 4);
        testQueue.enqueue(1, 1);
        testQueue.enqueue(3, 3);
        testQueue.enqueue(2, 2);
        testQueue.enqueue(1, 1);
        testQueue.enqueue(2, 2);

        List<Integer> results = new ArrayList<>();

        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        testQueue.enqueue(1, 1);
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());
        results.add(testQueue.dequeue());

        assertEquals(asList(1, 1, 1, 2, 2, 3, 4), results);
    }

    public void testEmptyQueueExceptionThrown() {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        try {
            testQueue.dequeue();
        } catch (ThrottleQueueException e) {
            assertEquals(EXCEPTION_PREFIX + ThrottleConstants.EXCEPTION_QUEUE_EMPTY.asString(),
                    e.getMessage());
        }
    }

    public void testFullQueueExceptionThrown() throws ThrottleQueueException {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(1);
        testQueue.enqueue(1, 1);
        try {
            testQueue.enqueue(1, 2);
        } catch (ThrottleQueueException e) {
            assertEquals(EXCEPTION_PREFIX + ThrottleConstants.EXCEPTION_QUEUE_FULL.asString(),
                    e.getMessage());
        }

    }
}
