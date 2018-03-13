package veinthrough.test;


/**
 * All unit testers will implement this interface.
 *
 * @author veinthrough
 */
public interface UnitTester {
    UnitTester setArgs( String[] args);
    String[] getArgs();

    /**
     * all unit testers should implement this interface.
     */
    void test();
}
