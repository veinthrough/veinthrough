/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.Closeable;
import java.io.IOException;
import static veinthrough.test.io.pipe.PipedStreamTest.TEST_MODE;
import java.io.PipedInputStream;

import lombok.Getter;
import lombok.Setter;

/**
 * @author veintrough
 * <p>---------------------------------------------------------
 * <p>the default max pipe buffer size is 1024
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. read message once.
 * 2. continued read messages.
 * </pre>
 *
 */
public class Receiver implements Runnable, Resetable, Closeable {
    //the default max pipe buffer size is 1024
    public static final int MAX_PIPE_BUFFER_SIZE = 1024;
    @Getter private PipedInputStream in = new PipedInputStream();;
    @Setter private TEST_MODE mode;

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        switch(mode) {
        case SHORT:
            readMessageOnce();break;
        case LONG:
            readMessageConntinued();break;
        }
    }

    private void readMessageOnce() {
        byte[] buf = new byte[MAX_PIPE_BUFFER_SIZE];
        try {
            //int len = in.read(buf);
            int len = in.read(buf, 0, MAX_PIPE_BUFFER_SIZE);
            if(len<MAX_PIPE_BUFFER_SIZE) {
                byte[] result = new byte[len];
                System.arraycopy(buf, 0, result, 0, len);
                System.out.printf("%4d bytes:%s\n", len, new String(result));
            } else {
                System.out.printf("%4d bytes:%s\n", len, new String(buf));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readMessageConntinued() {
        byte[] buf = new byte[MAX_PIPE_BUFFER_SIZE];
        int total = 0;
        while(total<=1024) {
            try {
                //int len = in.read(buf);
                int len = in.read(buf, 0, MAX_PIPE_BUFFER_SIZE);
                total += len;
                if(len<MAX_PIPE_BUFFER_SIZE) {
                    byte[] result = new byte[len];
                    System.arraycopy(buf, 0, result, 0, len);
                    System.out.printf("%4d bytes:%s\n", len, new String(result));
                } else {
                    System.out.printf("%4d bytes:%s\n", len, new String(buf));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        in = new PipedInputStream();
    }
}
