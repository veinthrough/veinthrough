/**
 *
 */
package veinthrough.test.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test.io.StreamRWTest
 * @see veinthrough.test.io.file.FileStreamTest
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * FileWriter(String fileName)
 * FileWriter(String fileName, boolean append)
 * FileWriter(File file)
 * FileWriter(File file, boolean append)
 * FileWriter(FileDescriptor fd)
 * 不使用charset作为参数，完全用OutputStreamWriter来构造
 *
 * FileReader(String fileName)
 * FileReader(File file)
 * FileReader(FileDescriptor fd)
 * 不适用charset作为参数，完全用InputStreamReader来构造
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 完全用OutputStreamWriter/InputStreamReader来实现
 * 没有 FileReader.readLine()
 * </pre>
 *
 */
public class FileRWTest extends AbstractUnitTester {
    private static final String FILE_NAME = "file_RW_test.txt";
    private static final int BUF_SIZE = 100;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        testWrite();
        testRead();
    }

    private void testWrite() {
        try (FileWriter out = new FileWriter(FILE_NAME)) {
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
        try(FileReader in = new FileReader(FILE_NAME)) {
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
        new FileRWTest().test();
    }

}
