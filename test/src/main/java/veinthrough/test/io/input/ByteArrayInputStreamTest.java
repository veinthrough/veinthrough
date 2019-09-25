/**
 *
 */
package veinthrough.test.io.input;

import java.io.ByteArrayInputStream;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. read 1 byte
 * 2. read bytes of a specified size
 * 3. mark and reset
 * </pre>
 *
 */
public class ByteArrayInputStreamTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {

        // corresponding to "abcdefghijklmnopqrsttuvwxyz"
        final byte[] LETTER_BYTE = {
                0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
                0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
                };
        final int SIZE_EACH = 5;

        ByteArrayInputStream bais = new ByteArrayInputStream(LETTER_BYTE);

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

    public static void main(String[] args) {
        new ByteArrayInputStreamTest().test();

    }

}
