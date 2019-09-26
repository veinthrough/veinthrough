/**
 *
 */
package veinthrough.test.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write and read
 * 2. write then flush and read
 * 3. mark/reset in read which is the same as ByteArrayInputStream
 * </pre>
 * <p>As mark/reset:
 * @see veinthrough.test.io.input.ByteArrayInputStreamTest#test()
 *
 */
public class BufferedStreamTest extends AbstractUnitTester {
    //corresponding to "abcdefghijklmnopqrsttuvwxyz"
    private static final byte[] LETTER_BYTE_ARRAY = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
            };
    private static final int SIZE_EACH = 5;
    private static final int SIZE_BUFFER =11;
    private static final String fileName = "test/file3.txt";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        /*
         * 5 bytes Written: abcde
         *  0 bytes available.
         * 5 bytes Written: fghij
         *  0 bytes available.
         * 5 bytes Written: klmno
         *  10 bytes available.
         *  10 bytes read: abcdefghij
         *  5 bytes available.
         *  5 bytes read: klmno
         */
        test1();
        /*
         * 5 bytes Written: abcde
         *  5 bytes available.
         *  5 bytes read: abcde
         * 5 bytes Written: fghij
         *  5 bytes available.
         *  5 bytes read: fghij
         * 5 bytes Written: klmno
         *  5 bytes available.
         *  5 bytes read: klmno
         *  0 bytes available.
         */
        test2();
    }

    public void test1() {
        try(BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(fileName), SIZE_BUFFER);
            BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(fileName))) {
            for(int i=0; i<SIZE_BUFFER/SIZE_EACH+1; i++) {
                //write
                byte[] written = Arrays.copyOfRange(LETTER_BYTE_ARRAY, i*SIZE_EACH, (i+1)*SIZE_EACH);
                bos.write(written);
                System.out.printf("%d bytes Written: %s\n", SIZE_EACH, new String(written));

                //read
                int available = bis.available();
                System.out.printf("    %d bytes available.\n", available);
                if(available>0) {
                    byte[] read = new byte[available];
                    int len = bis.read(read, 0, available);
                    System.out.printf("    %d bytes read: %s\n", len, new String(read));
                }
            }
            //flush and read at last
            bos.flush();
            int available = bis.available();
            System.out.printf("    %d bytes available.\n", available);
            if(available>0) {
                byte[] read = new byte[available];
                int len = bis.read(read, 0, available);
                System.out.printf("    %d bytes read: %s\n", len, new String(read));
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void test2() {
        try(BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(fileName), SIZE_BUFFER);
            BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(fileName))) {
            for(int i=0; i<SIZE_BUFFER/SIZE_EACH+1; i++) {
                //write
                byte[] written = Arrays.copyOfRange(LETTER_BYTE_ARRAY, i*SIZE_EACH, (i+1)*SIZE_EACH);
                bos.write(written);
                bos.flush();
                System.out.printf("%d bytes Written: %s\n", SIZE_EACH, new String(written));

                //read
                int available = bis.available();
                System.out.printf("    %d bytes available.\n", available);
                if(available>0) {
                    byte[] read = new byte[available];
                    int len = bis.read(read, 0, available);
                    System.out.printf("    %d bytes read: %s\n", len, new String(read));
                }
            }
            //flush and read at last
            bos.flush();
            int available = bis.available();
            System.out.printf("    %d bytes available.\n", available);
            if(available>0) {
                byte[] read = new byte[available];
                int len = bis.read(read, 0, available);
                System.out.printf("    %d bytes read: %s\n", len, new String(read));
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
        new BufferedStreamTest().test();
    }

}
