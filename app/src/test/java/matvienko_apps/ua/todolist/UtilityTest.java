package matvienko_apps.ua.todolist;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilityTest {
    @Test
    public void testDateTransform() throws Exception {
        String dateStr = Utility.fromCalendarToString(Calendar.getInstance());
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Utility.fromStringToLong(dateStr));


        assertEquals(Utility.fromCalendarToString(Calendar.getInstance()),Utility.fromCalendarToString(date));
    }

}