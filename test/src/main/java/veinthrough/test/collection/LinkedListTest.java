/**
 *
 */
package veinthrough.test.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>
 * skeleton:
 * <p>
 * 1. Can traverse list still when modifying(add/remove) the list.
 */
public class LinkedListTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        List<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        // merge the words from b into a
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();
        while (bIter.hasNext())
        {
            if (aIter.hasNext()) aIter.next();
            aIter.add(bIter.next());
        }
        System.out.println(a);

        // remove every second word from b
        bIter = b.iterator();
        while (bIter.hasNext())
        {
            bIter.next(); // skip one element
            if (bIter.hasNext())
            {
                bIter.next(); // skip next element
                bIter.remove(); // remove that element
            }
        }
        System.out.println(b);

        // bulk operation: remove all words in b from a
        a.removeAll(b);
        System.out.println(a);
    }

    public static void main(String[] args) {
        new LinkedListTest().test();
    }

}
