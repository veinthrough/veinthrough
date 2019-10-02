/**
 *
 */
package veinthrough.test.goova;

import java.util.Arrays;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

import veinthrough.test.AbstractUnitTester;
import veinthrough.test._class.Manager;
import veinthrough.test.generic.Pair;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * 1. [static]get Ordering<T> from Comparator<T>
 * 2. implement the abstract Ordering<T> by override compare()
 * 3. [static] natural()/usingToString()
 * 4. onResultOf()：对集合中元素调用 Function，再按返回值用当前排序器排序。
 * 5. reverse()
 * 6. nullsFirst()/nullsLast()
 * 7. fluentIterable:链式操作， 链式调用产生的排序器时，应该从后往前读；
 *  是因为每次链式调用都是用后面的方法包装了前面的排序 
 * </pre>
 *
 */
public class OrderingTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        Manager ceo = new Manager("Gus Greedy", 800000D, 80000D);
        Manager cfo = new Manager("Sid Sneaky", 600000D, 60000D);
        Manager[] managers = { ceo, cfo };
        
        minMax(managers);
        Pair<Manager> result = new Pair<>();
        minmaxBonus(managers, result);

    }

    public static <T extends Comparable<T>> Pair<T> minMax(T[] a) {
        return minMax(Arrays.asList(a));
    }
    
    public static <T extends Comparable<T>> Pair<T> minMax(Iterable<T> a) {
        Preconditions.checkArgument(a!=null && a.iterator().hasNext());
        //1. [static]get Ordering<T> from Comparator<T>
        Ordering<T> ordering = Ordering.<T>from(
                (left,right) -> left.compareTo(right));
        return Pair.<T>builder()
                .first(ordering.min(a))
                .second(ordering.max(a))
                .build();
        
        //2. implement the abstract Ordering<T> by override compare()
        /*
        Ordering<T> ordering = new Ordering<T>() {
            @Override
            public int compare(T arg0, T arg1) {
                return arg0.compareTo(arg1);
            }
        };
        */
    }
    
    public static void minmaxBonus(
            Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(Arrays.asList(a), result);
    }
    
    public static void minmaxBonus(
            Iterable<Manager> a, Pair<? super Manager> result) {
        Preconditions.checkArgument(a!=null && a.iterator().hasNext());
        //3. [static] natural()/usingToString()
        //4. onResultOf()
        //7. fluentIterable
        Ordering<Manager> ordering = Ordering.natural().onResultOf(
                manager -> manager.getBonus());
        result.setFirst(ordering.min(a));
        result.setSecond(ordering.max(a));
    }

    public static void main(String[] args) {
        new OrderingTest().test();
    }
}
