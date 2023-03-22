package lab.charge.custom.priority.queue;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class ThrottlingQueueTest extends TestCase {
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

    /**
     * Rigourous Test :-)
     */
    public void testThrottlingQueueExampleOne() {
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

        assertEquals(Arrays.asList(1, 1, 2, 1, 2, 3, 4), results);
    }

    public void testThrottlingQueueExampleTwo() {
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

        assertEquals(Arrays.asList(1, 2, 1, 2, 3, 4), results);
    }
}
