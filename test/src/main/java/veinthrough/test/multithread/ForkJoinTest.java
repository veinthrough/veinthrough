/**
 *
 */
package veinthrough.test.multithread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * skeleton:
 * <p>
 * This program demonstrates the fork-join framework:
 * <p>
 * calculate the rate of double numbers larger than 0.5 in 0...1 by conduct a 1000000 random test.
 */
public class ForkJoinTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        final int SIZE = 1000000;
        double[] numbers = new double[SIZE];
        for(int i=0; i<SIZE; i++) numbers[i] = Math.random();

        ForkJoinPool pool = new ForkJoinPool();
        Counter counter= new Counter(numbers, 0, numbers.length,
                                    number -> number>0.5);
        pool.invoke(counter);
        System.out.println((double)counter.join()/numbers.length);
    }

    public static void main(String[] args) {
        new ForkJoinTest().test();
    }

    @FunctionalInterface
    interface Filter {
        boolean accept(double t);
    }

    @SuppressWarnings("serial")
    class Counter extends RecursiveTask<Integer> {
        public static final int THRESHOLD = 1000;
        private double[] values;
        private int from;
        private int to;
        private Filter filter;

        public Counter(double[] values, int from, int to, Filter filter) {
            this.values = values;
            this.from = from;
            this.to = to;
            this.filter = filter;
        }

        @Override
        protected Integer compute() {
            if(to-from < THRESHOLD) {
                int count = 0;
                for(int i=from; i<to; i++) {
                    if(filter.accept(values[i])) count++;
                }
                return count;
            } else {
                int mid = (from + to) / 2;
                Counter first = new Counter(values, from, mid, filter);
                Counter second = new Counter(values, mid, to, filter);
                invokeAll(first, second);
                return first.join() + second.join();
            }
        }
    }
}
