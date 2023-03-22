package lab.charge.custom.priority.queue;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
    public void testThrottlingQueue_1() {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        testQueue.enqueue(4,4);
        testQueue.enqueue(1,1);
        testQueue.enqueue(3,3);
        testQueue.enqueue(2,2);
        testQueue.enqueue(1,1);
        testQueue.enqueue(2,2);

        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        testQueue.enqueue(1,1);
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
    }

    public void testThrottlingQueue_2() {
        ThrottlingQueue<Integer> testQueue = new ThrottlingQueueImpl<>(10);
        testQueue.enqueue(4,4);
        testQueue.enqueue(1,1);
        testQueue.enqueue(3,3);
        testQueue.enqueue(2,2);
        testQueue.enqueue(2,2);

        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        testQueue.enqueue(1,1);
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
        System.out.println(testQueue.dequeue());
    }
}
