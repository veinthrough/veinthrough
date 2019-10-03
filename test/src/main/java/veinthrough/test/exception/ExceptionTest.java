/**
 * 
 */
package veinthrough.test.exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * 1. Exceptions architecture.
 * (1) Throwable-+---Error
 *               |
 *               +---Exception-+---IOException
 *                             |
 *                             +---RuntimeException
 * (2) unchecked-+---Error
 *               |
 *               +---RuntimeException
 * (3) checked-------IOException
 * </pre>
 * <p>---------------------------------------------------------
 * <pre>
 * 1. 一个方法必须声明所有可能抛出的checked exception，
 * 而unchecked exception要么不可控制（Error），要么就应该避免发生（逻辑问题）。
 * 2. 再次抛出异常与异常链：在catch子句中可以抛出一个异常：
 * (1) 改变异常类型/抛出更详细的异常
 * (2) checked exception --> unchecked exception
 * 3. finnaly中也有可能抛出异常
 * (1) 使用2个try
 * (2) 使用try(resource)
 *  但是try(resource)中的close()抛出的exception将会被抑制
 * </pre>
 *
 */
public class ExceptionTest extends AbstractUnitTester {
    //make it non-existed
    private static final String fileName = "exception_test.txt";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        test1();
        test2();
    }
    
    //finnaly（调用close()）中也有可能抛出异常
    //(1) 使用2个try
    private void test1() {
        InputStream in = null;
        try {
            try {
                in = new FileInputStream(fileName);
                System.out.printf("Read:%d\n", in.read());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  finally {
                if(in!=null) {
                    in.close();
                }
            }
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
    }
    
    //finnaly（调用close()）中也有可能抛出异常
    //(2) 使用try(resource)
    //但是try(resource)中的close()抛出的exception将会被抑制
    private void test2() {
        try(InputStream in = new FileInputStream(fileName)) {
            System.out.printf("Read:%d\n", in.read());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        new ExceptionTest().test();
    }

}
