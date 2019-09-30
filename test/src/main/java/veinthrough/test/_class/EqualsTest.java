package veinthrough.test._class;

import veinthrough.test.AbstractUnitTester;

/**
 * This program demonstrates the equals method.
 * @author veinthrough
 */
public class EqualsTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        Employee alice1 = new Employee("Alice Adams", 75000D);
        Employee alice2 = alice1;
        Employee alice3 = new Employee("Alice Adams", 75000D);
        Employee bob = new Employee("Bob Brandson", 50000D);

        System.out.println("alice1 == alice2: " + (alice1 == alice2));

        System.out.println("alice1 == alice3: " + (alice1 == alice3));

        System.out.println("alice1.equals(alice3): " + alice1.equals(alice3));

        System.out.println("alice1.equals(bob): " + alice1.equals(bob));

        System.out.println("bob.toString(): " + bob);

        Manager carl = new Manager("Carl Cracker", 80000D, 80000D);
        Manager boss = new Manager("Carl Cracker", 80000D, 80000D);
        boss.setBonus(5000D);
        System.out.println("boss.toString(): " + boss);
        System.out.println("carl.equals(boss): " + carl.equals(boss));
        System.out.println("alice1.hashCode(): " + alice1.hashCode());
        System.out.println("alice3.hashCode(): " + alice3.hashCode());
        System.out.println("bob.hashCode(): " + bob.hashCode());
        System.out.println("carl.hashCode(): " + carl.hashCode());
    }

    public static void main(String[] args) {
        new EqualsTest().test();
    }
}