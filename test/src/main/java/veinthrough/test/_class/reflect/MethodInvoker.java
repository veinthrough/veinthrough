package veinthrough.test._class.reflect;

import java.lang.reflect.*;

import veinthrough.test.AbstractUnitTester;

/**
 * This program shows how to invoke methods through reflection.
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * APIs:
 * [Class]        Method getMethod(String name, Class<?>... parameterTypes)
 * [Method]        Object invoke(Object obj, Object... args):
 *                  调用static函数的第一个参数为null，否则第一个参数为调用该方法的对象
 * </pre>
 */
public class MethodInvoker extends AbstractUnitTester {
    @Override
    public void test() {
        // get method pointers to the square and sqrt methods
        try {
            Method square = MethodInvoker.class.getMethod("square", double.class);
            Method sqrt = Math.class.getMethod("sqrt", double.class);

            // print tables of x- and y-values
            printTable(1, 10, 10, square);
            printTable(1, 10, 10, sqrt);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        new MethodInvoker().test();
    }

    /**
    * Returns the square of a number
    * @param x a number
    * @return x squared
    */
    public static double square(double x)
    {
        return x * x;
    }

    /**
    * Prints a table with x- and y-values for a method
    * @param from the lower bound for the x-values
    * @param to the upper bound for the x-values
    * @param n the number of rows in the table
    * @param f a method with a double parameter and double return value
    */
    public static void printTable(double from, double to, int n, Method f)
    {
        // print out the method as table header
        System.out.println(f);

        double dx = (to - from) / (n - 1);

        for (double x = from; x <= to; x += dx)
        {
            try
            {
                //when calling static method, the first argument is null
                double y = (Double) f.invoke(null, x);
                System.out.printf("%10.4f | %10.4f%n", x, y);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
