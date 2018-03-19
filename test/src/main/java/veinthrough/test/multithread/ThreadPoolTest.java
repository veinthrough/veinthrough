/**
 *
 */
package veinthrough.test.multithread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * skeleton:
 * <p>
 * 1. Use thread pool to submit recursive callable task to find all keyword matches in files of a directory.
 * <p>
 * 2. thread pool should be shut down.
 * <p>
 * 3. can get largest pool size of cached thread pool during execution.
 */
public class ThreadPoolTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        String directory = "E:\\百度云\\Projects\\Eclipse\\veinthrough\\test\\src\\main\\java\\veinthrough\\test";
        String keyword = "AbstractUnitTester";
        ExecutorService pool = Executors.newCachedThreadPool();

        MatchCounter task = new MatchCounter(new File(directory), keyword, pool);
        try {
            System.out.println(pool.submit(task).get() + " matches.");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size=" + largestPoolSize);
    }

    public static void main(String[] args) {
        new ThreadPoolTest().test();
    }

    class MatchCounter implements Callable<Integer> {
        private File directory;
        private String keyword;
        private ExecutorService pool;

        public MatchCounter(File directory, String keyword, ExecutorService pool) {
            this.directory = directory;
            this.keyword = keyword;
            this.pool = pool;
        }

        @Override
        public Integer call() {
                int count = 0;
                if(!directory.isDirectory()) {
                    return search(directory);
                } else {
                    List<Future<Integer>> results = new ArrayList<>();
                    File[] files = directory.listFiles();
                    for(File file : files) {
                        if(!file.isDirectory()) {
                            count += search(file);
                        } else {
                            MatchCounter task = new MatchCounter(file, keyword, pool);
                            results.add(pool.submit(task));
                        }
                    }

                    for(Future<Integer> result : results) {
                        try {
                            count += result.get();
                        } catch (InterruptedException | ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    return count;
                }
        }

        public Integer search(File file) {
            Integer count = 0;
            try(Scanner in = new Scanner(file)) {
                while(in.hasNextLine()) {
                    String line = in.nextLine();
                    if(line.contains(keyword)) count++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return count;

        }
    }

}
