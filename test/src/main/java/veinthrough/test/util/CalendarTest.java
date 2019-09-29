/**
 *
 */
package veinthrough.test.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import veinthrough.test.AbstractUnitTester;

/**
 * @author veinthrough
 * @see venthrough.test.util.DateFormatTest
 *
 * <p>---------------------------------------------------------
 * <pre>
 * Tests contains:
 * 1. simulate a calendar app as follows:
 *  Sun Mon Tue Wed Thu Fri Sat
 *                   1   2   3
 *  4   5   6   7   8   9  10
 *  11  12  13  14* 15  16  17
 *  18  19  20  21  22  23  24
 *  25  26  27  28  29  30  31
 * 2. Calculate(by CalendarTest) and display(by DateFormatTest) now/tomorrow/next month/next year.
 * </pre>
 */
public class CalendarTest extends AbstractUnitTester {
    private static final TimeZone SHANG_HAI = TimeZone.getTimeZone("Asia/Shanghai");
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS z";

    /* (non-Javadoc)
     * @see veinthrough.test.UnitTester#test()
     */

    @Override
    public void test() {
        calendarSimulate();
        nextDateTest();
    }

    public void calendarSimulate() {
        //construct d as current date
        GregorianCalendar d= new GregorianCalendar();

        int today = d.get(Calendar.DAY_OF_MONTH);
        int month = d.get(Calendar.MONTH);

        //set d to start date of the month
        d.set(Calendar.DAY_OF_MONTH, 1);

        int weekday = d.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = d.getFirstDayOfWeek();

        //determine the required indentation for the first line
        int indent = 0;
        while(weekday != firstDayOfWeek) {
            indent++;
            d.add(Calendar.DAY_OF_MONTH, -1);
            weekday = d.get(Calendar.DAY_OF_WEEK);
        }

        //print weekday names
        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
        do {
            System.out.printf("%4s", weekdayNames[weekday]);
            d.add(Calendar.DAY_OF_MONTH, 1);
            weekday = d.get(Calendar.DAY_OF_WEEK);
        } while(weekday != firstDayOfWeek);
        System.out.println();

        for(int i=1; i<=indent; i++) {
            System.out.print("    ");
        }

        d.set(Calendar.DAY_OF_MONTH, 1);
        do {
            // print day
            int day = d.get(Calendar.DAY_OF_MONTH);
            System.out.printf("%3d", day);

            // mark current day with *
            if(day == today) System.out.print("*");
            else System.out.print(" ");

            // next day
            d.add(Calendar.DAY_OF_MONTH, 1);
            weekday = d.get(Calendar.DAY_OF_WEEK);

            // start a new line at the start of the week
            if(weekday == firstDayOfWeek) System.out.println();
        } while(d.get(Calendar.MONTH) == month);
        System.out.println();

    }

    private void nextDateTest() {
        DateFormat format = new DateFormatTest()
                .getDateFormat(DEFAULT_DATE_PATTERN, SHANG_HAI);
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

    public Calendar getCalendar(long time) {
        Calendar calendar= GregorianCalendar.getInstance();
        calendar.setTime(new Date(time));
        return calendar;
    }

    public long next(int field, long time) {
        Calendar calendar = getCalendar(time);
        calendar.add(field, 1);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        new CalendarTest().test();
    }

}
