/**
 *
 */
package veinthrough.test.generic;

import java.util.Arrays;

import veinthrough.test.generic.Pair;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test._class.Employee;
import veinthrough.test._class.Manager;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. XX = <? extends XX> is OK
 * 2. <? extends XX> = XX is NOT OK
 * 3. <? super XX> = XX is OK
 * 4. XX = <? super XX> is NOT OK
 * 5. <T extends Comparable<? super T>比较 <T extends Comparable<T>
 *  由于Employee implements Comparable<Employee> 和  Manager extends Employee；
 *  所以Manager 是 Comparable<Employee>的子类，Manager可以赋值给Comparable<? super Manager>；
 *  所以Manager不是Comparable<Manager>的子类，Manger不能赋值给Comparable<Manager>。
 *  Calendar与GregorianCalendar也是同样效果。
 * </pre>
 */
public class GenericTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        Manager ceo = new Manager("Gus Greedy", 800000D, 80000D);
        Manager cfo = new Manager("Sid Sneaky", 600000D, 60000D);
        Pair<Manager> ceo_cfo = new Pair<>(ceo, cfo);
        printBuddies(ceo_cfo);

        Pair<? extends Employee> wildcardBuddies = ceo_cfo;
        //2. <? extends Employee> is unable used to write
        //Employee can't be wrote to <? extends Employee>
        //Employee worker = new Employee("veinthrough", 80000D);
        //wildcardBuddies.setFirst(worker);        

        //1. <? extends Employee> is used to read
        // Employee can read from <? extends Employee>
        Employee cxo = wildcardBuddies.getFirst();
        System.out.println(cxo);

        Manager[] managers = { ceo, cfo };
        

        //<T extends Comparable<? super T>比较 <T extends Comparable<T>
        //  由于Employee implements Comparable<Employee> 和  Manager extends Employee
        //  所以Manager 是 Comparable<Employee>的子类，Manager可以赋值给Comparable<? super Manager>
        //  所以Manager不是Comparable<Manager>的子类，Manger不能赋值给Comparable<Manager>
        //  Calendar与GregorianCalendar也是同样效果
        Pair<Manager> result = new Pair<Manager>();
        minmaxBonus(managers, result);
        printBuddies(result);
        //Manager是 Comparable<Employee>的子类
        //Manager可以赋值给Comparable<? super Manager>
        PairAlg.<Manager>minMax(managers);
        //Manager不是Comparable<Manager>的子类
        //Manger不能赋值给Comparable<Manager>
        //PairAlg.<Manager>minMax2(managers);
        PairAlg.minMax2(managers);
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        //1. <? extends Employee> is used to read
        // Employee can read from <? extends Employee>
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println( "["+first.getName()+","+second.getName()+"]");
    }

    public static void minmaxBonus(
            Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(Arrays.asList(a), result);
    }

    public static void minmaxBonus(
            Iterable<Manager> a, Pair<? super Manager> result) {
        Preconditions.checkArgument(a!=null && a.iterator().hasNext());
        Ordering<Manager> ordering = Ordering.natural().onResultOf(
                manager -> manager.getBonus());
        //2. <? super Manager> is used to write
        // Pair<Manager> can be wrote to <? super Manager>
        result.setFirst(ordering.min(a));
        result.setSecond(ordering.max(a));
    }

    public static void main(String[] args) {
        new GenericTest().test();
    }

}

class PairAlg {
    //<T extends Comparable<? super T>
    //<T extends Comparable<T>
    public static <T extends Comparable<? super T>> Pair<T> minMax(T[] a) {
        Preconditions.checkArgument(a!=null && a.length>0);
        return minMax(Arrays.asList(a));
    }
    
    public static <T extends Comparable<T>> Pair<T> minMax2(T[] a) {
        Preconditions.checkArgument(a!=null && a.length>0);
        return minMax(Arrays.asList(a));
    }


    //
    public static <T extends Comparable<? super T>> Pair<T> minMax(Iterable<T> a) {
        Preconditions.checkArgument(a!=null && a.iterator().hasNext());
        //convert Comparator<T> to Ordering<T>
        Ordering<T> ordering = Ordering.<T>from(
                (left,right) -> left.compareTo(right));
        return Pair.<T>builder()
                .first(ordering.min(a))
                .second(ordering.max(a))
                .build();
        /*
        Ordering<T> ordering = new Ordering<T>() {
            @Override
            public int compare(T arg0, T arg1) {
                return arg0.compareTo(arg1);
            }
        };
        */
    }

    //
    public static <T extends Comparable<T>> Pair<T> minMax2(Iterable<T> a) {
        Preconditions.checkArgument(a!=null && a.iterator().hasNext());
        //convert Comparator<T> to Ordering<T>
        Ordering<T> ordering = Ordering.<T>from(
                (left,right) -> left.compareTo(right));
        return Pair.<T>builder()
                .first(ordering.min(a))
                .second(ordering.max(a))
                .build();
    }
}
