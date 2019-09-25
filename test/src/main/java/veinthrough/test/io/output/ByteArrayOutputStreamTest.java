/**
 *
 */
package veinthrough.test.io.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <p> Attention：no byte array parameter in constructor
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write a single byte
 * 2. write a specified size bytes
 * 3. convert to a byte array
 * 4. write to another output stream
 * </pre>
 *
 */
public class ByteArrayOutputStreamTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //corresponding to "abcdefghijklmnopqrsttuvwxyz"
        final byte[] LETTER_BYTE_ARRAY = {
                0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
                0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
                };
        final int SIZE_EACH = 5;

        //no byte array parameter in constructor
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //corresponding to "ABCDE"
        //write a single byte
        baos.write(0x41);
        baos.write(0x42);
        baos.write(0x43);
        baos.write(0x44);
        baos.write(0x45);
        System.out.printf("%3d bytes:%s\n", baos.size(), baos);

        //write a specified size bytes
        baos.write(LETTER_BYTE_ARRAY, 0, SIZE_EACH);
        System.out.printf("%3d bytes:%s\n", baos.size(), baos);

        //convert to a byte array
        byte[] buf = baos.toByteArray();
        System.out.printf("%3d bytes:%s\n", buf.length, new String(buf));

        //write to another output stream
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        try {
            baos.writeTo(baos2);
            System.out.printf("%3d bytes:%s\n", baos2.size(), baos2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ByteArrayOutputStreamTest().test();

    }

}
