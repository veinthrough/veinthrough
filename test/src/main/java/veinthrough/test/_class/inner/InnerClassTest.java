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
import veinthrough.test.AbstractUnitTester;
import veinthrough.test._class.reflect.ClassAnalyzer;

/**
 * test general inner class
 * @author veinthrough
 * <p>---------------------------------------------------------
 * <pre>
 * class veinthrough.test._class.inner.TalkingClock
   {
    //访问了beep,而beep为private，相当于beep的static访问器
    static boolean access$0(TalkingClock);
   }
   class veinthrough.test._class.inner.TalkingClock$TimePrinter
   {
    //在构造函数中初始化这些自动构造的域
    veinthrough.test._class.inner.TalkingClock$TimePrinter(TalkingClock);
    //非静态内部类将会生成引用外部类对象的域，该域为final
    final TalkingClock this$0;
   }
   </pre>
 *
 */

public class InnerClassTest extends AbstractUnitTester {
    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        classAnalysis();

        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();
    }

    private void classAnalysis() {
        ClassAnalyzer analyzer = new ClassAnalyzer();
        System.out.println(analyzer.analyze(TalkingClock.class));
        System.out.println(analyzer.analyze(TalkingClock.TimePrinter.class));
    }

    public static void main(String[] args) {
        new InnerClassTest().test();
    }
}

@AllArgsConstructor
class TalkingClock {
    private int interval;
    private boolean beep;

    public void start()
    {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    //内部类才可以用private/static修饰符
    class TimePrinter implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
