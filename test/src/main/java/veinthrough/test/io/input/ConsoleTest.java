/**
 *
 */
package veinthrough.test.io.input;

import java.io.Console;
import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * ---------------------------------------------------------
 * <p>
 * caution: System.console() will be null in eclipse
 * <p>
 * ---------------------------------------------------------
 * <p>
 * Tests contains:
 * <p>
 * How to use Console.
 *
 */
public class ConsoleTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //caution: console == null
        //it doesn't hava a console as eclipse run in Javaw mode
        Console console = System.console();
        if(console!=null) System.out.println("console:" + console.toString());
        String username = console.readLine("User name:");
        char[] passwd = console.readPassword("Password:");

        System.out.println( String.format("User name:%s, password:%s", username, passwd));
    }

    public static void main(String[] args) {
        new ConsoleTest().test();

    }

}
