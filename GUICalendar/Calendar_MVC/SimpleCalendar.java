import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}
enum DAYS
{
	Sun, Mon, Tue, Wed, Thur, Fri, Sat ;
}

/**
 * 
 * @author Stephanie Cheng
 *2016 cs151 summer
 */
public class SimpleCalendar 
{
	public static void main(String[] args) 
	{
		CalendarModel model = new CalendarModel();
		CalendarView view = new CalendarView(model);
		model.attach(view);
	}
}
