/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.Closeable;
import java.io.IOException;
import java.io.PipedOutputStream;

import lombok.Getter;
import lombok.Setter;
import veinthrough.test.io.pipe.PipedStreamTest.TEST_MODE;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <p>The default max pipe buffer size is 1024.
 *
 */
public class Sender implements Runnable, Resetable, Closeable {
    //the default max pipe buffer size is 1024
    public static final int MAX_PIPE_BUFFER_SIZE = 1024;
    @Getter private PipedOutputStream out = new PipedOutputStream();
    @Setter private TEST_MODE mode;

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        switch(mode) {
        case SHORT:
            writeShortMessage();break;
        case LONG:
            writeLongMessage();break;
        }
    }

    private void writeShortMessage() {
        String str = "This is a short message";
        try {
            //out.write(str.getBytes());
            out.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeLongMessage() {
        String str = constructLongMessage(MAX_PIPE_BUFFER_SIZE+2);
        try {
            //out.write(str.getBytes());
            out.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String constructLongMessage(int size) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<size/10; i++) {
            sb.append("0123456789");
        }
        for(int i=0; i<size%10; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        out = new PipedOutputStream();
    }
}
