/**
 *
 */
package veinthrough.test._class.inner;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import veinthrough.test.AbstractUnitTester;
import veinthrough.test._class.reflect.ClassAnalyzer;

/**
 * test static inner class
 * @author veinthrough
 * 不访问外围类对象，不会额外生成域
 *
 */

public class StaticInnerClassTest extends AbstractUnitTester {
    @Getter private static final boolean beep = false;
    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        classAnalysis();

        TalkingClock4 clock = new TalkingClock4(1000);
        clock.start();
    }

    private void classAnalysis() {
        ClassAnalyzer analyzer = new ClassAnalyzer();
        System.out.println(analyzer.analyze(TalkingClock4.class));
        System.out.println(analyzer.analyze(TalkingClock4.TimePrinter.class));
    }

    public static void main(String[] args) {
        new StaticInnerClassTest().test();
    }
}

@AllArgsConstructor
class TalkingClock4 {
    private int interval;

    public void start()
    {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    //内部类才可以用private/static修饰符
    //内部类不访问外部类对象，应置static
    static class TimePrinter implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Date now = new Date();
            System.out.println("At the tone, the time is " + now);
            if (StaticInnerClassTest.isBeep()) Toolkit.getDefaultToolkit().beep();
        }
    }
}
