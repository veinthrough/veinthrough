/**
 *
 */
package veinthrough.test.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import veinthrough.test.AbstractUnitTester;

/**
 * @author venthrough
 * <p>---------------------------------------------------------
 * <pre>
 * 1. please refer to document --Java IO Summary--
 * 2. DataInputStream extends FilterInputStream
 * 3. DataInputStream/DataOutputStream with ObjectInputStream/ObjectOutputStream:
 *  1) both can read/write basic types;
 *  2) both have readUTF()/writeUTF()
 *  3) ObjectInputStream has readObject();
 *     ObjectOutputStream has writeObject().
 * 4. writeUTF   -- readUTF, will write length as UTF contains length;
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. read/write basic types
 * 2. writeUTF()/readUTF()
 * </pre>
 *
 */
public class DataStreamTest extends AbstractUnitTester {
    private static final String FILE_NAME = "data_stream_test.txt";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        testWrite();
        testRead();
    }
    private void testWrite() {
        //located in classpath: .../Eclipse/veinthrough/test
        //the upper level path of src/main/java
        try(DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(FILE_NAME))) {
            dos.writeBoolean(true);
            dos.writeByte((byte)0x61);
            dos.writeChar('b');
            dos.writeShort((short)0x4445);
            dos.writeFloat(3.14F);
            dos.writeDouble(1.414D);
            dos.writeUTF("Veinthrough 冷");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void testRead() {
        try(DataInputStream dis = new DataInputStream(
                new FileInputStream(FILE_NAME))) {
            System.out.println(dis.readBoolean());
            System.out.printf("%#x\n", dis.readByte());
            System.out.printf("%c\n", dis.readChar());
            System.out.printf("%#x\n", dis.readShort());
            System.out.println(dis.readFloat());
            System.out.println(dis.readDouble());
            System.out.printf("%s\n", dis.readUTF());

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DataStreamTest().test();
    }
}
