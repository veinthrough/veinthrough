/**
 *
 */
package veinthrough.test._class.inner;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;
import lombok.AllArgsConstructor;
import lombok.Setter;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test._class.reflect.ClassAnalyzer;

/**
 * test local inner class
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * class veinthrough.test._class.inner.TalkingClock2
   {
    //访问了beep,而beep为private，相当于beep的static访问器
    static boolean access$0(TalkingClock2);
   }

   class veinthrough.test._class.inner.TalkingClock2$1TimePrinter
   {
    //在构造函数中初始化这些自动构造的域
    veinthrough.test._class.inner.TalkingClock2$1TimePrinter(TalkingClock2, Date, boolean);
    //非静态内部类将会生成引用外部类对象的域，该域为final
    final TalkingClock2 this$0;
    //访问的局部变量也会生成域，该域为final
    private final Date val$now;
    private final boolean val$beep_;
   }
 * </pre>
 *
 */

public class LocalInnerClassTest extends AbstractUnitTester {
    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        classAnalysis();

        TalkingClock2 clock = new TalkingClock2(1000, true);
        clock.start(1000, true);
    }

    private void classAnalysis() {
        System.out.println(
                new ClassAnalyzer().analyze(TalkingClock2.class));
    }

    public static void main(String[] args) {
        new LocalInnerClassTest().test();
    }
}

@AllArgsConstructor
class TalkingClock2 {
    private int interval;
    @Setter private boolean beep;

    //Local variable accessed by local inner class must be final or effectively final;
    //即接下来没有被修改，所以最好把局部内部类访问的局部变量置final
    public void start(int interval_, final boolean beep_)
    {
        final Date now = new Date();
        //局部内部类只能是abstract/final
        class TimePrinter implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                System.out.println("At the tone, the time is " + now);
                //局部内部类可以访问外部类域
                //局部内部类也可以访问局部变量，但必须为final
                if (beep | beep_) Toolkit.getDefaultToolkit().beep();
            }
        }

        //修改局部内部类访问的局部变量，那么就不是effectively final
        //now = new Date(new CalendarTest().next(Calendar.MONTH, now.getTime()));

        System.out.println(
                new ClassAnalyzer().analyze(TimePrinter.class));
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }
}
