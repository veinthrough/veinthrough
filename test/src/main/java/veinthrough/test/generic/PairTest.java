/**
 *
 */
package veinthrough.test.generic;

import java.util.Arrays;
import java.util.Comparator;

import veinthrough.test.AbstractUnitTester;
import veinthrough.test.temp.Employee;
import veinthrough.test.temp.Manager;

/**
 * @author veinthrough
 *
 * skeleton:
 * <p>
 * 1. <? extends Employee> is used to read, unable used to write:
 * <p>
 *    Employee can't be wrote to <? extends Employee>.
 * 2. <? super Manager> is used to write:
 * <p>
 *    Pair<Manager> can be wrote to <? super Manager>.
 * <p>
 * 3. swapHelper:
 *    How to create a wildcard variable.
 */
public class PairTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        // print managers
        Manager ceo = new Manager("Gus Greedy", 800000, 2003, 12, 15);
        Manager cfo = new Manager("Sid Sneaky", 600000, 2003, 12, 15);
        Pair<Manager> buddies = new Pair<>(ceo, cfo);
        printBuddies(buddies);

        // <? extends Employee> is unable used to write
        // Employee can't be wrote to <? extends Employee>
        Employee worker = new Employee("veinthrough", 10000, 2003, 12, 15);
        Pair<? extends Employee> wildcardBuddies = buddies;
        // (? extends Employee)is not applicable for the arguments (Employee)
        //wildcardBuddies.setFirst(worker);

        ceo.setBonus(1000000);
        cfo.setBonus(500000);
        Manager[] managers = { ceo, cfo };

        // Pair<Manager> can be wrote to <? super Manager>
        Pair<Manager> result = new Pair<>();
        minmaxBonus(managers, result);
        System.out.println("first: " + result.getFirst().getName()
         + ", second: " + result.getSecond().getName());
        maxminBonus(managers, result);
        System.out.println("first: " + result.getFirst().getName()
         + ", second: " + result.getSecond().getName());
    }

    // <? extends Employee> is used to read
    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println( first.getName() + " and " + second.getName() + " are buddies.");
    }

    // <? super Manager> is used to write, to store result
    public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if(a == null || a.length == 0) return;
        Comparator<Manager> comparator= (managerA, managerB) ->
                        (int) (managerA.getBonus() - managerB.getBonus());
        // Pair<Manager> can be wrote to <? super Manager>
        result.setFirst(Arrays.asList(a).stream().min(comparator).get());
        result.setSecond(Arrays.asList(a).stream().max(comparator).get());
    }

    public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a, result);
        PairAlg.swap(result);
    }

    public static void main(String[] args) {
        new PairTest().test();
    }

}

class Pair<T> {
    private T first;
    private T second;

    public Pair() { first = null; second = null;}
    public Pair(T first, T second) { this.first = first; this.second = second;}

    public T getFirst() { return this.first; }
    public T getSecond() { return this.second; }

    public void setFirst( T newValue) { this.first = newValue; }
    public void setSecond( T newValue) { this.second = newValue; }
}

class PairAlg {
    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }

    // use swapHelper as you can't:
    // ? t = p.getFirst()
    public static void swap(Pair<?> p) {
        swapHelper(p);
    }
    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }
}
