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
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * skeleton:
 * <p>
 * Use recursive future to find all keyword matches in files of a directory.
 *
 */
public class FutureTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        String directory = "E:\\百度云\\Projects\\Eclipse\\veinthrough\\test\\src\\main\\java\\veinthrough\\test";
        String keyword = "AbstractUnitTester";

        FutureTask<Integer> task = new FutureTask<>(
                                        new MatchCounter(new File(directory), keyword));
        new Thread(task).start();
        try {
            System.out.println(task.get() + " matches.");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FutureTest().test();
    }

    class MatchCounter implements Callable<Integer> {
        private File directory;
        private String keyword;

        public MatchCounter(File directory, String keyword) {
            this.directory = directory;
            this.keyword = keyword;
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
                            FutureTask<Integer> task = new FutureTask<>(
                                    new MatchCounter(file, keyword));
                            results.add(task);
                            new Thread(task).start();
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
