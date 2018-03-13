package veinthrough.test;


import java.util.ArrayList;
import java.util.List;

import com.veinthrough.test.env.CheckForClass;
import com.veinthrough.test.env.GetEnv;
import com.veinthrough.test.env.SysProp;

/**
 * @author veinthrough
 */
public class MultiTester {

    /**
     * a list of all testers to be executed.
     */
    List<UnitTester> unitTesters= new ArrayList<>();

    public static void main(String[] args) {
        MultiTester tester= new MultiTester();

        tester.add( new GetEnv());
        tester.add( new SysProp().setArgs( args));
        tester.add( new CheckForClass().setArgs( args));

        tester.test();
    }

    /**
     * Add a tester to the list which will be executed.
     *
     * @param unitTester tester to add
     * @return           always this to provide a sequence calling
     */
    public MultiTester add( UnitTester unitTester) {
        this.unitTesters.add(unitTester);
        return this;
    }

    /**
     * Execute all testers added to the list.
     *
     * Use {@link #add(UnitTester)} to add a tester to the list
     */
    public void test() {
        if(!this.unitTesters.isEmpty()) {
            for( UnitTester unitTester: unitTesters) {
                System.out.println(unitTester.getClass().getSimpleName()+"----------");
                unitTester.test();
                System.out.println("-----------------------------------------------------------\n");
            }
        }
    }
}