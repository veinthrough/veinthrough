/**
 *
 */
package veinthrough.test.io.input;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test.resource.ResourceRetriever;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. standard input test.
 * 2. file input test with next()/ nextInt().
 * 3. file input test with nextLine()
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * next() compared with nextLine():
 * 1. next() will trim whitespace before the valid chars.
 * 2. next() treat whitespace as spliter.
 * 3. nextLine() treat line.separator as spliter.
 * System.getProperty("line.separator")
 * 4. next() will never get whitespace as result.
 * </pre>
 *
 */
@RequiredArgsConstructor
public class ScannerTest extends AbstractUnitTester {
    @NonNull ResourceRetriever resourceRetriever;


    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        //standard input test
        test1();
        //file input test with next()/ nextInt()
        test2();
        //file input test with nextLine()
        test3();
    }

    //standard input test
    private void test1() {
        //try with resource
        //automatically release resource
        try(Scanner in = new Scanner(System.in)) {
            System.out.print("Name:");
            String name = in.nextLine();

            System.out.print("Age:");
            Integer age = in.nextInt();

            System.out.println( String.format("Name:%s, Age:%d", name, age));
        }
    }

    //file input test with next()/ nextInt()
    private void test2() {
        try{
            String fileName = resourceRetriever.getExactFileNameOfResource(
                    "resource_ResourceReader.properties");
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            while(in.hasNext()) {
                if(in.hasNextInt()) {
                    System.out.printf("int: %d\n", in.nextInt());
                } else {
                    System.out.println("string:" + in.next());
                }
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //file input test with nextLine()
    private void test3() {
        ResourceRetriever resourceRetriever = new ResourceRetriever();
        try{
            String fileName = resourceRetriever.getExactFileNameOfResource(
                    "resource_ResourceReader.properties");
            File file = new File(fileName);
            Scanner in = new Scanner(file);

            Integer line = 0;
            while(in.hasNextLine()) {
                System.out.printf("line %d: %s\n", ++line, in.nextLine());
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ScannerTest(new ResourceRetriever()).test();

    }

}
