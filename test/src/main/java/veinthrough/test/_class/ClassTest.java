/**
 *
 */
package veinthrough.test._class;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test._class.DerivedTest
 * @see veinthrough.test._class.Employee
 * @see veinthrogh.test.C_plus_plus.ParamTest
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. [Employee]私有数据域+公有域访问器+公有域更改器，这里用lombok实现.
 * 2. [Employee]final 域，类似于指针常量，没有@Setter:
 * 3. [ParamTest]Java中的参数传递都为值传递:
 *  ParamTest
 * 4. [Employee]构造函数：如果定义了有参构造函数，也应该定义无参构造函数
    new/未初始化对象都会默认调用无参构造函数
 * </pre>
 *
 */
public class ClassTest extends AbstractUnitTester {

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
        new ClassTest().test();
    }

}
