/**
 *
 */
package veinthrough.test.goova;

import veinthrough.test.AbstractUnitTester;
import veinthrough.test.resource.ResourceRetriever;

/**
 * @author veinthrough
 * <p>
 * ---------------------------------------------------------
 * <p>
 * The test is the same as {@link veinthrough.test.io.SourceAndSink}.
 *
 */
public class SourceAndSink extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        new veinthrough.test.io.SourceAndSink(new ResourceRetriever()).test();
    }

    public static void main(String[] args) {
        new SourceAndSink().test();
    }

}
