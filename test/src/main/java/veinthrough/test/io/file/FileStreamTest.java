/**
 *
 */
package veinthrough.test.io.file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * FileOutputStream(File file)
 * FileOutputStream(File file, boolean append)
 * FileOutputStream(String path)
 * FileOutputStream(String path, boolean append)
 * FileOutputStream(FileDescriptor fd)
 * FileInputStream(String name)
 * FileInputStream(File file)
 * FileInputStream(FileDescriptor fd)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write in default mode and read
 * 2. write in append mode and read
 * 3. file descriptor test
 * </pre>
 * @see veinthrough.test.io.file.FileTest#fileDescriptorTest()
 *
 */
public class FileStreamTest extends AbstractUnitTester {
    private static final int READ_LENGTH = 100;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        System.out.println("default mode:");
        String fileName = "test/file1.txt";
        test1(fileName);
        System.out.println("append mode:");
        test2(fileName);

    }

    private void test1(String fileName) {
        try(FileOutputStream fos1 = new FileOutputStream(fileName)) {
                fos1.write('A');
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try(FileOutputStream fos2 = new FileOutputStream(fileName)) {
                fos2.write('a');
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try(FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buf = new byte[READ_LENGTH];
            int len = fis.read(buf, 0, READ_LENGTH);
            if(len<READ_LENGTH) {
                byte[] result = new byte[len];
                System.arraycopy(buf, 0, result, 0, len);
                System.out.printf("%4d bytes:%s\n", len, new String(result));
            } else {
                System.out.printf("%4d bytes:%s\n", len, new String(buf));
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //file descriptor test
    private void test2(String fileName) {
        try(FileOutputStream fos1 = new FileOutputStream(fileName)) {
                fos1.write('A');
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //FileOutputStream(String name, boolean append)
        //append mode
        try(FileOutputStream fos2 = new FileOutputStream(fileName, true)) {
                fos2.write('a');
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try(FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buf = new byte[READ_LENGTH];
            int len = fis.read(buf, 0, READ_LENGTH);
            if(len<READ_LENGTH) {
                byte[] result = new byte[len];
                System.arraycopy(buf, 0, result, 0, len);
                System.out.printf("%4d bytes:%s\n", len, new String(result));
            } else {
                System.out.printf("%4d bytes:%s\n", len, new String(buf));
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
        new FileStreamTest().test();
    }

}
