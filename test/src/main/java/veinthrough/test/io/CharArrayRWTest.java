/**
 *
 */
package veinthrough.test.io;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test.io.ByteArrayStreamTest
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * CharArrayReader(char buf[])
 * CharArrayReader(char buf[], int offset, int length)
 * CharArrayWriter():
 *   The buffer capacity is initially 32, though its size increases if necessary
 * CharArrayWriter(int initialSize):
 *   The buffer capacity is initialized as the specified size
 *
 * [Attention]：no byte array parameter in CharArrayWriter constructor.
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. CharArrayReader/ByteArrayInputStream:
 *  1) CharArrayReader.ready()/ByteArrayInputStream.available();
 *  2) CharArrayReader will throw exception but ByteArrayInputStream wll not.
 * 2. CharArrayWriter/ByteArrayOutputStream:
 *  1) write(String)/null
 *  2) toCharArray()/toByteArray()
 *  3) append(),append(CharSequence csq),append(CharSequence csq, int start, int end)/null
 * 3. CharArrayReader.ready()/CharArrayWriter.size()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write/append/read a single char
 * 2. write/append/read a specified size chars
 * 3. write/append/read a CharSeqence
 * 4. read: mark and reset
 * 5. write: convert to a char array
 * 6. write: write to another output stream
 * </pre>
 *
 */
public class CharArrayRWTest extends AbstractUnitTester {

    private static final char[] LETTER_CHAR_ARRAY = new char[]{'a','b','c','d','e','f','g',
            'h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final int SIZE_EACH = 5;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        try {
            readTest();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writeTest();
    }

    private void readTest() throws IOException {
        CharArrayReader car = new CharArrayReader(LETTER_CHAR_ARRAY);

        int pos = 1;
        while(pos<=SIZE_EACH &&
              car.ready()){
            //read 1 byte
            char read = (char)car.read();
            System.out.printf("%d: %c\n", pos++, read, read);
        }

        //check whether mark is supported
        if(!car.markSupported()) {
            System.out.println("mark not supported!");
            return;
        }

        System.out.println("mark supported!");

        //Set the current marked position in the stream.
        //parameter: readAheadLimit, it has no meaning for this class
        //@see java.io.BufferedInputStream#mark(Int)
        car.mark(0);
        int mark = pos;

        car.skip(SIZE_EACH);
        pos += SIZE_EACH;

        char[] buf = new char[SIZE_EACH];
        //read bytes of a specified size
        car.read(buf, 0, SIZE_EACH);
        System.out.printf("[%d-%d]: %s\n", pos, pos+SIZE_EACH-1,
                new String(buf));
        pos += SIZE_EACH;

        //reset position to the mark
        car.reset();
        pos = mark;

        car.read(buf, 0, SIZE_EACH);
        System.out.printf("[%d-%d]: %s\n", pos, pos+SIZE_EACH-1,
                new String(buf));
        pos += SIZE_EACH;
    }

    private void writeTest() {
        //no byte array parameter in constructor
        CharArrayWriter caw = new CharArrayWriter();
        //corresponding to "ABCDE"
        //write a single byte
        caw.write('A');

        //write string
        try {
            caw.write("BC");
            System.out.printf("%3d chars:%s\n", caw.size(), caw);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //write a specified size chars
        caw.write(LETTER_CHAR_ARRAY, 0, SIZE_EACH);
        System.out.printf("%3d chars:%s\n", caw.size(), caw);

        //append
        caw.append('0')
            .append("123456789")
            .append(new String(LETTER_CHAR_ARRAY), 8, 12);
        System.out.printf("%3d chars:%s\n", caw.size(), caw);

        //convert to a byte array
        char[] buf = caw.toCharArray();
        System.out.printf("%3d chars:%s\n", buf.length, new String(buf));

        //write to another output stream
        CharArrayWriter caw2 = new CharArrayWriter();
        try {
            caw.writeTo(caw2);
            System.out.printf("%3d chars:%s\n", caw2.size(), caw2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CharArrayRWTest().test();
    }

}
