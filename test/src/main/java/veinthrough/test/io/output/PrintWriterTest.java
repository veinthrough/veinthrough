/**
 *
 */
package veinthrough.test.io.output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.common.base.Charsets;
import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test.io.output.PrintStream
 * <p>---------------------------------------------------------
 * <pre>
 * PrintStream/PrintWriter:
 * 2个类的功能基本相同，PrintStream能做的PrintWriter也都能实现，并且PrintWriter的功能更为强大。
 * 但是由于PrintWriter出现的比较晚，较早的System.out使用的是PrintStream来实现的，所以为了兼容就没有废弃PrintStream。
 * 2个类最大的差别是:
 *  1) PrintStream在输出字符，将字符转换为字节时采用的是系统默认的编码格式，
 *  这样当数据传输另一个平台，而另一个平台使用另外一个编码格式解码时就会出现问题，存在不可控因素。
 *  而PrintWriter可以在传入Writer时可由程序员指定字符转换为字节时的编码格式，这样兼容性和可控性会更好。
 *  2) 多了PrintWriter(Writer out)
 *  3) PrintStream是字节流，它有处理raw byte的方法，write(int)和write(byte[],int,int)；
 *  PrintWriter是字符流，它没有处理raw byte的方法。
 *  4) PrintStream和PrintWriter的auto flushing机制有点不同，前者在输出byte数组、调用println方法、输出换行符或者byte值10（即\n）时自动调用flush方法;
 *  后者仅在调用println方法时发生auto flushing。
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * 1. manually-configured auto-flush with OutputStream.
 *  PrintWriter(OutputStream out)
 *  PrintWriter(OutputStream out, boolean autoFlush)
 *
 * 2. no auto-flush parameter coordinated with file parameter,
 * will automatically create OutputStreamWriter and FileOutputStream.
 *  PrintWriter(String fileName)
 *  PrintWriter(String fileName, String charSetName)
 *  PrintWriter(File file)
 *  PrintWriter(File file, String charSetName)
 *
 * 3. manually-configured auto-flush with Writer.
 * [additional constructors compared with PrintStream]
 *  PrintWriter(Writer out)
 *  PrintWriter(Writer out, boolean autoFlush)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * PrintStream/DataOutputStream:
 * 1. PrintStream是输出时采用的是用户指定的编码(创建PrintStream时指定的)，若没有指定，则采用系统默认的字符编码。
 *  而DataOutputStream则采用的是UTF-8。
 * 2. DataOutputStream的作用是装饰其它的输出流，它和DataInputStream配合使用：允许应用程序以与机器无关的方式从底层输入流中读写java数据类型。
 *  而PrintStream的作用虽然也是装饰其他输出流，但是它的目的不是以与机器无关的方式从底层读写java数据类型；而是为其它输出流提供打印各种数据值
 *  表示形式(字符串形式)，使其它输出流能方便的通过print(), println()或printf()等输出各种格式的数据。
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. new a PrintWriter by a file name and charset.
 * 2. write chinese string and check error.
 * </pre>
 */
public class PrintWriterTest extends AbstractUnitTester {
    private static final String FILE_NAME = "print_writer_test.txt";
    PrintWriter a;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        try(PrintWriter pw = new PrintWriter(FILE_NAME, Charsets.UTF_8.name())) {
            pw.append('a');
            pw.printf("My name is: %s\n", "Veinthrough 冷");
            System.out.printf("Has error: %b\n", pw.checkError());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrintWriterTest().test();
    }

}
