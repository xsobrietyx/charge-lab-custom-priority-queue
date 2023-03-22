package lab.charge.custom.priority.queue.exception;

/**
 * Created by xsobrietyx on 22-March-2023 time 10:08
 */
public class ThrottleQueueException extends Exception{
    public ThrottleQueueException(String message) {
        super("[ThrottleQueue exception]:" + message);
    }
}
