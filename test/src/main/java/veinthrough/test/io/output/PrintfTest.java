/**
 *
 */
package veinthrough.test.io.output;

import java.util.Date;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 *
 * pattern:
 * <p>
 * __%__________________________________________________________________________conversion_______
 *      |                  |   |        |  |         |  |    |                |              |
 *      |___param index__$_|   |__flag__|  |__width__|  |    |__.__precision__|              |
 *                                                      |_____t_____time conversion__________|
 *
 */
public class PrintfTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        /*flag: ,
         *precision: 2
         *conversion: f
         */
        System.out.printf("%,.2f\n", 10000.0/3.0);

        //width: 6
        System.out.printf("%,6.2f\n", 10000.0/3.0);

        //width: 8
        System.out.printf("%,8.2f\n", 10000.0/3.0);

        //width: 10
        System.out.printf("%,10.2f\n", 10000.0/3.0);

        /*
         * converson: s
         * param index: 1, 2
         * time conversion: B,e,Y
         *
         */
        System.out.printf("%1$s %2$tB %2$te, %2$tY\n", "Due date:", new Date());

    }

    public static void main(String[] args) {
        new PrintfTest().test();

    }

}
