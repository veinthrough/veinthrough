/**
 *
 */
package veinthrough.test.io.output;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import com.google.common.base.Charsets;
import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * PrintStream:
 * 1. extends FilterOutputStream
 * 2. System.out is a PrintStream
 * 3. PrintStream will never throw a IOException, otherwise, you can call checkError()
 *    to check whether a IOException is thrown.
 * 4. auto-flush
 * 5. function of set charset/encoding in constructor
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * 1. manually-configured auto-flush with OutputStream
 *  PrintStream(OutputStream out)
 *  PrintStream(OutputStream out, boolean autoFlush)
 *  PrintStream(OutputStream out, boolean autoFlush, String encoding)
 * 2. no auto-flush with file, will automaticall create FileOutputStream
 *  PrintStream(String fileName)
 *  PrintStream(String fileName, String charSetName)
 *  PrintStream(File file)
 *  PrintStream(File file, String charSetName)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * PrintStream with DataOutputStream:
 * 1. PrintStream是输出时采用的是用户指定的编码(创建PrintStream时指定的)，若没有指定，则采用系统默认的字符编码。
 *  而DataOutputStream则采用的是UTF-8。
 * 2. DataOutputStream的作用是装饰其它的输出流，它和DataInputStream配合使用：允许应用程序以与机器无关的方式从底层输入流中读写java数据类型。
 *  而PrintStream的作用虽然也是装饰其他输出流，但是它的目的不是以与机器无关的方式从底层读写java数据类型；而是为其它输出流提供打印各种数据值
 *  表示形式(字符串形式)，使其它输出流能方便的通过print(), println()或printf()等输出各种格式的数据。
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. new a PrintStream by a file name and charset.
 * 2. check error.
 * </pre>
 */
public class PrintStreamTest extends AbstractUnitTester {
    private static final String FILE_NAME = "print_stream_test.txt";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        try(PrintStream ps = new PrintStream(FILE_NAME, Charsets.UTF_8.name())) {
            ps.append('a');
            ps.printf("My name is: %s\n", "Veinthrough 冷");
            System.out.printf("Has error: %b\n", ps.checkError());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrintStreamTest().test();
    }

}
