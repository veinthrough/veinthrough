/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * @see Receiver
 * @see Sender
 *
 */
public class PipedStreamTest extends AbstractUnitTester {
    private Receiver receiver = new Receiver();
    private Sender sender = new Sender();
    ExecutorService pool= Executors.newCachedThreadPool();

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //1. connect to each other
        connect();
        _test(TEST_MODE.SHORT);

        reset();
        _test(TEST_MODE.LONG);

        close();
    }

    private void connect() {
        PipedInputStream pis = receiver.getIn();
        PipedOutputStream pos = sender.getOut();
        try {
            pis.connect(pos);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void reset() {
        receiver.reset();
        sender.reset();
        connect();
    }

    private void close() {
        pool.shutdown();
        receiver.close();
        sender.close();
    }

    @SuppressWarnings("unchecked")
    private void _test(TEST_MODE mode) {
        //2. set modes
        receiver.setMode(mode);
        sender.setMode(mode);

        //3. start tasks
        try {
            pool.invokeAll(
                    Lists.newArrayList(
                            () -> {receiver.run();return null;},
                            () -> {sender.run();return null;}));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PipedStreamTest().test();
    }

    public enum TEST_MODE {
        SHORT,
        LONG;
    }

}
