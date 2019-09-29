/**
 *
 */
package veinthrough.test.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * 有点像FileStream+DataStream+FileReader/FileWriter(readLine()其实是在BufferedReader里面)
 * <pre>
 * constructors:
 * 1. RandomAccessFile(String name, String mode)
 * 2. RandomAccessFile(File file, String mode)
 *
 * mode:
 *  "r"    以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
 *  "rw"   打开以便读取和写入。
 *  "rws"  打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
 *  "rwd"  打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. getFilePointer():Returns the current offset in this file.
 * 2. skipBytes()
 * 3. seek()
 * 4. setLength()：缩小或者扩展文件
 *  缩小：将会影响getFilePointer()如果缩小到比file pointer指向的更小的范围；
 *  扩展：扩展部分是undefined.
 * 5. readLine()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. different mode: r/rw
 * 2. writeChars()
 * 3. writeUTF()/readUTF()
 * 4. getPointer()/length()/seek()/skipBytes()
 * 5. readLine()
 * 6. number of bytes of different basic types
 * </pre>
 *
 */
public class RandomAccessFileTest extends AbstractUnitTester {
    private static final String fileName = "random_access_file_test.txt";

    private int base_type_length = 0;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        File file = new File(fileName);
        if (file.exists())
            file.delete();

        testCreateWrite();
        testAppendWrite();
        testRead();

    }

    private void testCreateWrite() {
        //read-write mode
        try(RandomAccessFile raf = new RandomAccessFile(fileName, "rw");) {
            raf.writeChars("abcdefghijklmnopqrstuvwxyz\n");
            System.out.printf("File length: %d\n", raf.length());
            raf.writeChars("9876543210\n");
            System.out.printf("File length: %d\n", raf.length());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void testAppendWrite() {
        //read-write mode
        try(RandomAccessFile raf = new RandomAccessFile(fileName, "rw");) {
            long fileLen = raf.length();
            System.out.printf("File length: %d\n", raf.length());
            raf.seek(fileLen);

            raf.writeBoolean(true);                 base_type_length += 1;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeByte(0x41);                    base_type_length += Byte.BYTES;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeChar('a');                     base_type_length += 2;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeShort(0x3c3c);                 base_type_length += Short.BYTES;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeInt(0x75);                     base_type_length += Integer.BYTES;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeLong(0x1234567890123456L);     base_type_length += Long.BYTES;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeFloat(4.7f);                   base_type_length += Float.BYTES;
            System.out.printf("File length: %d\n", raf.length());
            raf.writeDouble(8.256);                 base_type_length += Double.BYTES;
            System.out.printf("File length: %d\n", raf.length());

            raf.writeUTF("veinthrough 冷\n");
            System.out.printf("File length: %d\n", raf.length());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void testRead() {
        //read-only mode
        try(RandomAccessFile raf = new RandomAccessFile(fileName, "r");) {
            //read 'a'
            System.out.printf("readChar(): %c\n", raf.readChar());
            //read "abcdefghijklmnopqrstuvwxyz\n"
            System.out.printf("readLine(): %s\n", raf.readLine());

            //read "9876543210\n"
            raf.seek(raf.getFilePointer()+22);
            //the same effect
            //raf.skipBytes(22);

            //read base types: boolean, byte, char, short, int, long, float, double
            byte[] buf = new byte[base_type_length];
            raf.read(buf, 0, buf.length);

            //read "veinthrough 冷\n"
            System.out.printf("readUTF(): %s\n", raf.readUTF());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RandomAccessFileTest().test();
    }

}
