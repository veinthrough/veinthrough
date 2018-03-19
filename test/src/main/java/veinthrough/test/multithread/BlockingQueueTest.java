/**
 *
 */
package veinthrough.test.multithread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 *<p>
 * skeleton:
 * <p>
 * Multi-threads co-work to find all keyword matches in files of a directory by BlockingQueue:
 * <p>
 * 1 thread to list all files to a queue;
 * <p>
 * another multi-threads to search all keyword matches in each file.
 */
public class BlockingQueueTest extends AbstractUnitTester {
    public static File DUMMY = new File("");
    final int FILE_QUEUE_SIZE = 10;
    final int SEARCH_THREADS = 10;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        String directory = "E:\\百度云\\Projects\\Eclipse\\veinthrough\\test\\src\\main\\java\\veinthrough\\test";
        String keyword = "AbstractUnitTester";
        BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);
        new Thread(new FileEnumerationTask(queue, new File(directory)))
                       .start();
        for(int i=1; i<=SEARCH_THREADS; i++) {
            new Thread(new SearchTask(queue, keyword))
                            .start();
        }
    }

    public static void main(String[] args) {
        new BlockingQueueTest().test();
    }

    class FileEnumerationTask implements Runnable {
        // The field DUMMY cannot be declared static in a non-static inner type,
        // unless initialized with a constant expression
        //public static File DUMMY = new File("");
        private BlockingQueue<File> queue;
        private File startingDirectory;

        public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory) {
            this.queue = queue;
            this.startingDirectory = startingDirectory;
        }

        public void enumerate(File directory) throws InterruptedException {
            File[] files = directory.listFiles();
            for(File file : files) {
                if(file.isDirectory()) enumerate(file);
                //may throw InterruptedException
                else queue.put(file);
            }
        }

        @Override
        public void run() {
            try {
                if(startingDirectory.isDirectory()) {
                    enumerate(startingDirectory);
                } else {
                    queue.put(startingDirectory);
                }
                queue.put(DUMMY);
            } catch(InterruptedException e) {
            }
        }
    }

    class SearchTask implements Runnable {
        private BlockingQueue<File> queue;
        private String keyword;

        public SearchTask(BlockingQueue<File> queue, String keyword) {
            this.queue = queue;
            this.keyword = keyword;
        }

        @Override
        public void run() {
            try {
                File file = queue.take();
                while(file != DUMMY) {
                    search(file);
                    file = queue.take();
                }
                queue.put(file);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private void search(File file) throws FileNotFoundException {
            try(Scanner in = new Scanner(file)) {
                int lineNumber = 0;
                while(in.hasNextLine()) {
                    lineNumber++;
                    String line = in.nextLine();
                    if(line.contains(keyword)) {
                        System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
                    }
                }
            }
        }
    }

}
