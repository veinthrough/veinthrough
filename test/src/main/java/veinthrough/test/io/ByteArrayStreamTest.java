/**
 *
 */
package veinthrough.test.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * ByteArrayInputStream(byte buf[])
 * ByteArrayInputStream(byte buf[], int offset, int length)
 * ByteArrayOutputStream()
 * ByteArrayOutputStream(int size)
 *
 * [Attention]：no byte array parameter in constructor
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write/read a single byte
 * 2. write/read a specified size bytes
 * 3. read: mark and reset
 * 4. write: convert to a byte array
 * 5. write: write to another output stream
 * </pre>
 *
 */
public class ByteArrayStreamTest extends AbstractUnitTester {

    // corresponding to "abcdefghijklmnopqrsttuvwxyz"
    final byte[] LETTER_BYTE_ARRAY = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
            };
    final int SIZE_EACH = 5;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        readTest();
        writeTest();
    }

    private void readTest() {
        ByteArrayInputStream bais = new ByteArrayInputStream(LETTER_BYTE_ARRAY);

        int pos = 1;
        while(pos<=SIZE_EACH &&
              bais.available()>=0){
            //read 1 byte
            byte read = (byte) bais.read();
            System.out.printf("%d(%#x): %c\n", pos++, read, read);
        }

        //check whether mark is supported
        if(!bais.markSupported()) {
            System.out.println("mark not supported!");
            return;
        }

        System.out.println("mark supported!");

        //Set the current marked position in the stream.
        //parameter: readAheadLimit, it has no meaning for this class
        //@see java.io.BufferedInputStream#mark(Int)
        bais.mark(0);
        int mark = pos;

        bais.skip(SIZE_EACH);
        pos += SIZE_EACH;

        byte[] buf = new byte[SIZE_EACH];
        //read bytes of a specified size
        bais.read(buf, 0, SIZE_EACH);
        System.out.printf("[%d-%d]: %s\n", pos, pos+SIZE_EACH-1,
                new String(buf));
        pos += SIZE_EACH;

        //reset position to the mark
        bais.reset();
        pos = mark;

        bais.read(buf, 0, SIZE_EACH);
        System.out.printf("[%d-%d]: %s\n", pos, pos+SIZE_EACH-1,
                new String(buf));
        pos += SIZE_EACH;
    }

    private void writeTest() {
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
        new ByteArrayStreamTest().test();
    }

}
