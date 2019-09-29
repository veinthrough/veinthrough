/**
 *
 */
package veinthrough.test.C_plus_plus;

import veinthrough.test.AbstractUnitTester;
import veinthrough.test.lombok.Employee;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * This program demonstrates parameter passing in Java, contains:
 * 1. Methods can't modify numeric parameters.
 * 2. Methods can change the state of object parameters.
 * 3. Methods can't attach new objects to object parameters, that is, can't swap object parameters.
 * </pre>
 *
 */
public class ParamTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        /*
         * Test 1: Methods can't modify numeric parameters
         */
        System.out.println("Testing tripleValue:");
        double percent = 10;
        System.out.println("Before: percent=" + percent);
        tripleValue(percent);
        System.out.println("After: percent=" + percent);

        /*
         * Test 2: Methods can change the state of object parameters
         */
        System.out.println("\nTesting tripleSalary:");
        Employee harry = new Employee("Harry", 50000D, 2019, 9, 9);
        System.out.println("Before: salary=" + harry.getSalary());
        tripleSalary(harry);
        System.out.println("After: salary=" + harry.getSalary());

        /*
         * Test 3: Methods can't attach new objects to object parameters, that is, can't swap object parameters.
         */
        System.out.println("\nTesting swap:");
        Employee a = new Employee("Alice", 70000D, 2019, 9, 9);
        Employee b = new Employee("Bob", 60000D, 2019, 9, 9);
        System.out.println("Before: a=" + a.getName());
        System.out.println("Before: b=" + b.getName());
        swap(a, b);
        System.out.println("After: a=" + a.getName());
        System.out.println("After: b=" + b.getName());
    }

    private static void tripleValue(double x) // doesn't work
    {
        x = 3 * x;
        System.out.println("End of method: x=" + x);
    }

    private static void tripleSalary(Employee x) // works
    {
        x.raiseSalary(200);
        System.out.println(": salary=" + x.getSalary());
    }

    private static void swap(Employee x, Employee y)
    {
        Employee temp = x;
        x = y;
        y = temp;
        System.out.println("End of method: x=" + x.getName());
        System.out.println("End of method: y=" + y.getName());
    }

    public static void main(String[] args) {
        new ParamTest().test();
    }
}
