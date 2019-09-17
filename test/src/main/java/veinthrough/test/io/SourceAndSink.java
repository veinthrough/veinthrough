/**
 *
 */
package veinthrough.test.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test.resource.ResourceRetriever;

/**
 * @author veinthrough
 * <p>
 * ---------------------------------------------------------
 * <p>
 * Guava source/sink abstract some common(general-purpose) operations
 * from different kinds of char/byte stream.
 * <p>
 * source operations: read()/hash()/readLines()/copyTo()/size()/isEmpty()/contentEquals()
 * <p>
 * sink operations: write()/writeFrom()/writeLines()
 * <p>
 * ---------------------------------------------------------
 * <p>
 * Tests contains:
 * <p>
 * 1. char source, readLines().
 * <p>
 * 2. char source, read().
 * <p>
 * 3. byte source, hash().
 * <p>
 * 4. byte source, copyTo() and byte sink.
 *
 */
@RequiredArgsConstructor
public class SourceAndSink extends AbstractUnitTester {
    @NonNull ResourceRetriever resourceRetriever;

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        try {
            String fileName = resourceRetriever.getExactFileNameOfResource(
                    "resource_ResourceReader.properties");

            test1(fileName);
            test2(fileName);
            test3(fileName);
            test4(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //char source, readLines()
    private void test1(String fileName) {
        File file = new File(fileName);
        try {
            ImmutableList<String> lines = Files.asCharSource(file, Charsets.UTF_8)
                    .readLines();
            Iterator<String> iterator = lines.iterator();
            Integer line = 0;
            while(iterator.hasNext()) {
                System.out.printf("Line %d: %s\n", ++line, iterator.next());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //char source, read()
    private void test2(String fileName) {
        File file = new File(fileName);
        try {
            Multiset<String> wordOccurrences = HashMultiset.create(
                    Splitter.on(CharMatcher.WHITESPACE)
                    .trimResults()
                    .omitEmptyStrings()
                    .split(Files.asCharSource(file, Charsets.UTF_8).read()));
            for(String element: wordOccurrences.elementSet()) {
                System.out.printf("%s : %d\n", element, wordOccurrences.count(element));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //byte source, hash()
    private void test3(String fileName) {
        File file = new File(fileName);
        try {
            HashCode hash = Files.asByteSource(file).hash(Hashing.sha1());
            System.out.println(hash.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //byte source, copyTo()
    //byte sink
    private void test4(String fileName) {
        File file = new File(fileName);
        try {
            Resources.asByteSource(new URL("https://github.com/veinthrough/veinthrough/blob/master/README.md"))
                     .copyTo(Files.asByteSink(file));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SourceAndSink(new ResourceRetriever()).test();
    }

}
