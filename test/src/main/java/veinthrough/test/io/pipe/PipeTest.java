/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Map;
import java.util.concurrent.Executors;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import lombok.Getter;
import lombok.Setter;
import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * @see Receiver
 * @see Sender
 *
 */
public class PipeTest extends AbstractUnitTester {
    private static final boolean INIT = true;
    private static final boolean RESET = false;
    private static final long INTERVAL = 200;
    private int finished = 0;
    private Receiver receiver = new Receiver();
    private Sender sender = new Sender();
    @Setter private TEST_MODE mode;
    ListeningExecutorService pool =
            MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        _test(TEST_MODE.BYTE_SHORT, INIT);finishing();
        _test(TEST_MODE.BYTE_LONG, RESET);finishing();
        _test(TEST_MODE.CHAR_SHORT, RESET);finishing();
        _test(TEST_MODE.CHAR_LONG, RESET);finishing();
        close();
    }

    private void _test(TEST_MODE mode, boolean init) {
        //1. initialize/reset both
        if(init) {
            initialize(mode);
        } else {
            reset(mode);
        }

        //2. connect to each other
        connect();

        //3. start tasks
        //start sender
        finished = 0;
        Futures.addCallback(pool.submit(sender), new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                finished += FINISH.SENDER_FINISHED.value;
            }
            @Override
            public void onFailure(Throwable t) {
                System.out.printf("Sender[%s] failed as %s.\n", mode, t.getMessage());
            }
        });
        //start receiver
        Futures.addCallback(pool.submit(receiver), new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                finished += FINISH.RECEIVER_FINISHED.value;
            }
            @Override
            public void onFailure(Throwable t) {
                System.out.printf("Receiver[%s] failed as %s.\n", mode, t.getCause());
            }
        });
    }

    private void initialize(TEST_MODE mode) {
        setMode(mode);
        receiver.initialize(mode);
        sender.initialize(mode);
    }

    private void reset(TEST_MODE mode) {
        setMode(mode);
        receiver.reset(mode);
        sender.reset(mode);
    }

    private void connect() {
        //1. connect to each other
        switch(mode){
        case BYTE_SHORT:
        case BYTE_LONG:
            PipedInputStream pis = receiver.getIn_stream();
            PipedOutputStream pos = sender.getOut_stream();
            try {
                pis.connect(pos);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        case CHAR_LONG:
        case CHAR_SHORT:
            PipedReader reader = receiver.getReader();
            PipedWriter writer = sender.getWriter();
            try {
                reader.connect(writer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        default:
            break;
        }
    }

    private void finishing() {
        while(finished!=FINISH.BOTH_FINISHED.value) {
            System.out.printf("[%s]!\n", FINISH.forValue(finished));
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.printf("[%s]!\n", FINISH.forValue(finished));
    }

    private void close() {
        pool.shutdown();
        receiver.close();
        sender.close();
    }

    public static void main(String[] args) {
        new PipeTest().test();
    }

    public enum TEST_MODE {
        BYTE_SHORT,
        BYTE_LONG,
        CHAR_SHORT,
        CHAR_LONG;
    }

    private enum FINISH {
        NEITHER_FINISHED(0),
        RECEIVER_FINISHED(1),
        SENDER_FINISHED(2),
        BOTH_FINISHED(3)
        ;

        @Getter private int value;
        private static final Map<Integer, FINISH> VALUE_MAP;

        static {
            final ImmutableMap.Builder<Integer, FINISH> map = ImmutableMap.builder();
            for(FINISH enumItem : FINISH.values()) {
                map.put(enumItem.getValue(), enumItem);
            }
            VALUE_MAP = map.build();
        }

        private FINISH(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase().replace("_", " ");
        }

        public static FINISH forValue(Integer value) {
            return VALUE_MAP.get(value);
        }
    }

}
