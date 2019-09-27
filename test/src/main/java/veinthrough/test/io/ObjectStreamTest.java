/**
 *
 */
package veinthrough.test.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.common.collect.ImmutableMap;

import veinthrough.test.AbstractUnitTester;
import veinthrough.test.io.Serializable.User;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * constructors:
 * ObjectInputStream(InputStream in)
 * ObjectOutputStream(OutputStream out)
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. NO ObjectInputStream.available()
 * 2. NO ObjectOutputStream.size()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. handle basic types
 * 2. handle system-defined serializable types
 * 3. handle user-defined serializable types
 * </pre>
 * @see veinthrough.test.io.Serializable.User
 *
 */
public class ObjectStreamTest extends AbstractUnitTester {
    private static final String FILE_NAME = "object_stream_test.txt";

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
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            oos.writeBoolean(true);
            oos.writeByte((byte)0x61);
            oos.writeChar('b');
            oos.writeFloat(3.14F);
            oos.writeDouble(1.414D);

            //write map, a system serializable object
            ImmutableMap<Integer,String> map = ImmutableMap.<Integer,String>builder()
                    .put(1, "red")
                    .put(2, "green")
                    .put(3, "blue")
                    .build();
            oos.writeObject(map);

            //write self-defined serializable object
            User user = new User("veinthrough", "123456", "Beijing");
            oos.writeObject(user);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void testRead() {
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {
            System.out.println(ois.readBoolean());
            System.out.printf("%#x\n", ois.readByte());
            System.out.printf("%c\n", ois.readChar());
            System.out.println(ois.readFloat());
            System.out.println(ois.readDouble());
            System.out.println(ois.readObject());
            System.out.println(ois.readObject());

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ObjectStreamTest().test();
    }

}
