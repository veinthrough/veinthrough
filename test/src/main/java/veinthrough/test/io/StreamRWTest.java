/**
 *
 */
package veinthrough.test.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.common.base.Charsets;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * 1.OutputStreamWriter(OutputStream out)
 * 2.OutputStreamWriter(OutputStream out, String charsetName)
 * 3.OutputStreamWriter(OutputStream out, Charset cs)
 * 4.OutputStreamWriter(OutputStream out, CharsetEncoder enc)
 *
 * 1.InputStreamReader(InputStream out)
 * 2.InputStreamReader(InputStream out, String charsetName)
 * 3.InputStreamReader(InputStream out, Charset cs)
 * 4.InputStreamReader(InputStream out, CharsetEncoder enc)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. InputStreamReader.ready()/ NO OutputStreamWriter.size()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. Used different charsets in OutputStreamWriter/InputStreamReader
 * US_ASCII can't encoding 中文
 * </pre>
 *
 */
public class StreamRWTest extends AbstractUnitTester {
    private static final String FILE_NAME = "stream_RW_test.txt";
    private static final int BUF_SIZE = 100;
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    @SuppressWarnings("unused")
    private static final Charset US_ASCII = Charset.forName(Charsets.US_ASCII.name());
    private static final Charset GB2312 = Charset.forName("GB2312");
    private static final Charset GBK = Charset.forName("GBK");

    private Charset charset;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @SuppressWarnings("unused")
    @Override
    public void test() {
        _test(DEFAULT_CHARSET);
        _test(GB2312);
        _test(GBK);
        //_test(US_ASCII);
    }

    private void _test(Charset charset) {
        this.charset = charset;
        System.out.println("charset: " + charset.name());
        testWrite();
        testRead();
    }

    private void testWrite() {
        try (OutputStreamWriter out = new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), charset)) {
            out.write("姓名: veinthrough 冷\n");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void testRead() {
        try(InputStreamReader in = new InputStreamReader(
                new FileInputStream(FILE_NAME), charset)) {
            if(in.ready()) {
                char[] buf = new char[BUF_SIZE];
                int len = in.read(buf, 0, BUF_SIZE);
                System.out.printf("%d chars:%s\n", len, new String(buf, 0 , len));
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StreamRWTest().test();
    }

}
