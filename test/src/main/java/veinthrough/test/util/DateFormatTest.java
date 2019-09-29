/**
 *
 */
package veinthrough.test.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see veinthrough.test.util.CalendarTest
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. Calculate(by CalendarTest) and display(by DateFormatTest) now/tomorrow/next month/next year.
 * </pre>
 *
 */
public class DateFormatTest extends AbstractUnitTester {
    private static final TimeZone SHANG_HAI = TimeZone.getTimeZone("Asia/Shanghai");
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS z";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */
    @Override
    public void test() {
        DateFormat format = getDateFormat(DEFAULT_DATE_PATTERN, SHANG_HAI);
        CalendarTest calendar = new CalendarTest();

        long now = System.currentTimeMillis();
        System.out.printf("%12s: %s\n", "Now", format.format(new Date(now)));
        System.out.printf("%12s: %s\n", "Tomorrow",
                format.format(new Date(calendar.next(Calendar.DAY_OF_MONTH, now))));
        System.out.printf("%12s: %s\n", "Next month",
                format.format(new Date(calendar.next(Calendar.MONTH, now))));
        System.out.printf("%12s: %s\n", "Next year",
                format.format(new Date(calendar.next(Calendar.YEAR, now))));

    }

    public DateFormat getDateFormat(String pattern, TimeZone timeZone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern );
        format.setTimeZone(timeZone);
        return format;
    }

    public String formatDate(long date, String pattern, TimeZone timeZone) {
        return getDateFormat(pattern, timeZone).format(new Date(date));
    }

    public static void main(String[] args) {
        new DateFormatTest().test();
    }

}
