/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.Closeable;
import java.io.IOException;
import static veinthrough.test.io.pipe.PipeTest.TEST_MODE;
import java.io.PipedInputStream;
import java.io.PipedReader;

import com.google.common.base.Preconditions;

import lombok.Getter;

/**
 * @author veintrough
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * 1.PipedInputStream():
 *  default pipe buffer size is 1024
 * 2.PipedInputStream(int pipeSize)
 * 3.PipedInputStream(PipedOutputStream src):
 *  default pipe buffer size is 1024
 * 4.PipedInputStream(PipedOutputStream src, int pipeSize)
 * 1.PipedOutputStream()
 * 2.PipedOutputStream(PipedInputStream snk)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. read()/receive():receive()为非public，read()为public；并且从构造函数看PipedOutputStream没有buf，实际上基本所有的任务都是
 *  PipedInputStream来完成的，PipedOutputStream.write()实际上调用了连接的PipedInputStream.receive().
 * 2. PipedInputStream.available()
 * 3. NO PipedOutputStream.size()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. read bytes/chars once.
 * 2. continued read bytes/chars.
 * </pre>
 *
 */
public class Receiver
    implements Runnable, Initiative<TEST_MODE>, Resetable<TEST_MODE>, Closeable {
    //the default max pipe buffer size is 1024
    public static final int DEFAULT_PIPE_BUFFER_SIZE = 1024;
    @Getter private PipedInputStream in_stream;
    @Getter private PipedReader reader;
    private TEST_MODE mode;

    private void setMode(TEST_MODE mode) {
        this.mode = mode;
        switch(mode){
            case BYTE_SHORT:
            case BYTE_LONG:
                in_stream = new PipedInputStream();
                break;
            case CHAR_LONG:
            case CHAR_SHORT:
                reader = new PipedReader();
                break;
            default:
                break;
        }
    }
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        switch(mode) {
        case BYTE_SHORT:
            readByteMessageOnce();break;
        case BYTE_LONG:
            readByteMessageConntinued();break;
        case CHAR_LONG:
            readCharMessageConntinued();break;
        case CHAR_SHORT:
            readCharMessageOnce();break;
        default:
            break;
        }
    }

    private void readByteMessageOnce() {
        Preconditions.checkNotNull(in_stream);

        byte[] buf = new byte[DEFAULT_PIPE_BUFFER_SIZE];
        try {
            //int len = in_stream.read(buf);
            int len = in_stream.read(buf, 0, DEFAULT_PIPE_BUFFER_SIZE);
            System.out.printf("%4d bytes:%s\n", len, new String(buf, 0, len));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readCharMessageOnce() {
        Preconditions.checkNotNull(reader);

        char[] buf = new char[DEFAULT_PIPE_BUFFER_SIZE];
        try {
            //int len = reader.read(buf);
            int len = reader.read(buf, 0, DEFAULT_PIPE_BUFFER_SIZE);
            System.out.printf("%4d chars:%s\n", len, new String(buf, 0, len));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void readByteMessageConntinued() {
        Preconditions.checkNotNull(in_stream);

        byte[] buf = new byte[DEFAULT_PIPE_BUFFER_SIZE];
        int total = 0;
        while(total<=1024) {
            try {
                //int len = in_stream.read(buf);
                int len = in_stream.read(buf, 0, DEFAULT_PIPE_BUFFER_SIZE);
                System.out.printf("%4d bytes:%s\n", len, new String(buf, 0, len));
                total += len;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void readCharMessageConntinued() {
        Preconditions.checkNotNull(reader);

        char[] buf = new char[DEFAULT_PIPE_BUFFER_SIZE];
        int total = 0;
        while(total<=1024) {
            try {
                //int len = reader.read(buf);
                int len = reader.read(buf, 0, DEFAULT_PIPE_BUFFER_SIZE);
                System.out.printf("%4d chars:%s\n", len, new String(buf, 0, len));
                total += len;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            if(null!=in_stream) {
                in_stream.close();
            }
            if(null!=reader) {
                reader.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(TEST_MODE mode) {
        setMode(mode);
    }
    @Override
    public void reset(TEST_MODE mode) {
        close();
        in_stream = null;
        reader = null;
        setMode(mode);
    }
}
