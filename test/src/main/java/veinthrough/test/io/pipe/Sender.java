/**
 *
 */
package veinthrough.test.io.pipe;

import java.io.Closeable;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PipedWriter;

import com.google.common.base.Preconditions;

import lombok.Getter;
import veinthrough.test.io.pipe.PipeTest.TEST_MODE;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * 1.PipedInputStream():
 *  default pipe buffer size is 1024
 * 2.PipedInputStream(int pipeSize)
 * 3.PipedInputStream(PipedOutputStream src):
 *  default pipe buffer size is 1024
 * 4.PipedInputStream(PipedOutputStream src, int pipeSize)
 * PipedReader is the same as PipedInputStream
 *
 * 1.PipedOutputStream()
 * 2.PipedOutputStream(PipedInputStream snk)
 *  PipedOutputStream没有buf
 * PipedWriter is the same as PipedOutputStream
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. read()/receive():receive()为非public，read()为public；并且从构造函数看PipedOutputStream没有buf，实际上基本所有的任务都是
 *  PipedInputStream来完成的，PipedOutputStream.write()实际上调用了连接的PipedInputStream.receive().
 * 2. PipedInputStream.available()
 * 3. NO PipedOutputStream.size()
 * </pre>
 *
 */
public class Sender
    implements Runnable, Initiative<TEST_MODE>, Resetable<TEST_MODE>, Closeable {
    //the default max pipe buffer size is 1024
    public static final int MAX_PIPE_BUFFER_SIZE = 1024;
    @Getter private PipedOutputStream out_stream;
    @Getter private PipedWriter writer;
    private TEST_MODE mode;

    private void setMode(TEST_MODE mode) {
        this.mode = mode;
        switch(mode) {
        case BYTE_SHORT:
        case BYTE_LONG:
            out_stream = new PipedOutputStream();
            break;
        case CHAR_LONG:
        case CHAR_SHORT:
            writer = new PipedWriter();
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
            writeByteShortMessage();break;
        case BYTE_LONG:
            writeByteLongMessage();break;
        case CHAR_LONG:
            writeCharLongMessage();break;
        case CHAR_SHORT:
            writeCharShortMessage();break;
        default:
            break;
        }
    }

    private void writeByteShortMessage() {
        Preconditions.checkNotNull(out_stream);

        String str = "This is a short message";
        try {
            //out_stream.write(str.getBytes());
            out_stream.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeCharShortMessage() {
        Preconditions.checkNotNull(writer);

        String str = "This is a short message";
        try {
            //writer.write(str.toCharArray());
            writer.write(str.toCharArray(), 0, str.length());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeByteLongMessage() {
        Preconditions.checkNotNull(out_stream);

        String str = constructLongMessage(MAX_PIPE_BUFFER_SIZE+2);
        try {
            //out_stream.write(str.getBytes());
            out_stream.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeCharLongMessage() {
        Preconditions.checkNotNull(writer);

        String str = constructLongMessage(MAX_PIPE_BUFFER_SIZE+2);
        try {
            //writer.write(str);
            writer.write(str, 0, str.length());
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
            if(null!=out_stream) {
                out_stream.close();
            }
            if(null!=writer) {
                writer.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void reset(TEST_MODE mode) {
        close();
        out_stream = null;
        writer = null;
        setMode(mode);
    }

    @Override
    public void initialize(TEST_MODE mode) {
        setMode(mode);
    }
}
