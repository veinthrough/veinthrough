/**
 *
 */
package veinthrough.test.input;

import java.io.Console;
import java.util.Scanner;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * caution: System.console() will be null in eclipse
 * <p>
 * Tests contains:
 * <p>
 * 1. How to use Scanner.
 * 2. How to use Console.
 *
 */
public class InputTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //try with resources
        try(Scanner in = new Scanner(System.in)) {
            System.out.print("Name:");
            String name = in.nextLine();

            System.out.print("Age:");
            Integer age = in.nextInt();

            System.out.println( String.format("Name:%s, Age:%d", name, age));
        }

        //caution: console == null
        //it doesn't hava a console as eclipse run in Javaw mode
        Console console = System.console();
        if(console!=null) System.out.println("console:" + console.toString());
        String username = console.readLine("User name:");
        char[] passwd = console.readPassword("Password:");

        System.out.println( String.format("User name:%s, password:%s", username, passwd));
    }

    public static void main(String[] args) {
        new InputTest().test();

    }

}
