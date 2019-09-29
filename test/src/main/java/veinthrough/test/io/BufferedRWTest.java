/**
 *
 */
package veinthrough.test.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test.io.BufferedStreamTest
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * BufferedReader(Reader in)
 *  default buffer size:8192
 * BufferedReader(Reader in, int size)
 * BufferedWriter(Writer out, int size)
 *  default buffer size:8192
 * BufferedWriter(Writer out, int size)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. BufferedWriter.newLine()/BufferedReader.readLine()
 * 2. BufferedReader.ready()/BufferedInputStream.available()
 *  available:only available chars in buffer, not the available chars of the whold bottom-layer stream.
 * 3. NO BufferedWriter.size().
 * 4. BufferedReader.lines()
 *  Java1.8才加入，返回Stream<String>支持流式操作，
 *  比如List<String>.stream返回Stream<String>
 * 5. BufferedReader.mark()/BufferedReader.reset()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. write and read[很奇怪和BufferedStreamTest的运行结果不一样]
 * 2. write then flush and read
 * 3. mark/reset in read which is the same as ByteArrayInputStream
 * </pre>
 * <p>As mark/reset:
 * @see veinthrough.test.io.input.ByteArrayStreamTest#readTest()
 *
 */
public class BufferedRWTest extends AbstractUnitTester {

    private static final char[] LETTER_CHAR_ARRAY = new char[]{'a','b','c','d','e','f','g',
            'h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final int SIZE_EACH = 5;
    private static final int SIZE_BUFFER =11;
    private static final String fileName = "buffered_RW_test.txt";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        /*
         * 5 chars Written: abcde
         *  0 chars available.
         * 5 chars Written: fghij
         *  0 chars available.
         * 5 chars Written: klmno
         *  10 chars available.
         *  10 chars read: abcdefghij
         *  5 chars available.
         *  5 chars read: klmno
         */
        autoFlushTest();
        /*
         * 5 chars Written: abcde
         *  5 chars available.
         *  5 chars read: abcde
         * 5 chars Written: fghij
         *  5 chars available.
         *  5 chars read: fghij
         * 5 chars Written: klmno
         *  5 chars available.
         *  5 chars read: klmno
         *  0 chars available.
         */
        manualFlushTest();
    }

    public void autoFlushTest() {
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(fileName), SIZE_BUFFER);
            BufferedReader br = new BufferedReader(
                new FileReader(fileName), SIZE_BUFFER)) {

            int remaing = (SIZE_BUFFER/SIZE_EACH+1)*SIZE_EACH;
            for(int i=0; i<SIZE_BUFFER/SIZE_EACH+1; i++) {
                //write
                char[] written = Arrays.copyOfRange(LETTER_CHAR_ARRAY, i*SIZE_EACH, (i+1)*SIZE_EACH);
                //will auto flush in the last round
                bw.write(written);
                System.out.printf("%d chars Written: %s\n", SIZE_EACH, new String(written));

                //read
                if(br.ready()) {
                    int size = (i+1)*SIZE_EACH;
                    char[] read = new char[size];
                    int len = br.read(read, 0, size);
                    remaing -= len;
                    System.out.printf("    %d chars read: %s\n", len, new String(read, 0, len));
                }
            }
            //flush and read at last
            bw.flush();
            if(br.ready()) {
                char[] read = new char[remaing];
                int len = br.read(read, 0, remaing);
                System.out.printf("    %d chars read: %s\n", len, new String(read, 0, len));
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void manualFlushTest() {
        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter(fileName), SIZE_BUFFER);
            BufferedReader br = new BufferedReader(
                new FileReader(fileName), SIZE_BUFFER)) {

            int remaing = (SIZE_BUFFER/SIZE_EACH+1)*SIZE_EACH;
            for(int i=0; i<SIZE_BUFFER/SIZE_EACH+1; i++) {
                //write
                char[] written = Arrays.copyOfRange(LETTER_CHAR_ARRAY, i*SIZE_EACH, (i+1)*SIZE_EACH);
                bw.write(written);
                //manually flush
                bw.flush();
                System.out.printf("%d chars Written: %s\n", SIZE_EACH, new String(written));

                //read
                if(br.ready()) {
                    int size = (i+1)*SIZE_EACH;
                    char[] read = new char[size];
                    int len = br.read(read, 0, size);
                    remaing -= len;
                    System.out.printf("    %d chars read: %s\n", len, new String(read, 0, len));
                }
            }
            //flush and read at last
            bw.flush();
            if(br.ready()) {
                char[] read = new char[remaing];
                int len = br.read(read, 0, remaing);
                System.out.printf("    %d chars read: %s\n", len, new String(read, 0, len));
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
        new BufferedRWTest().test();
    }

}
