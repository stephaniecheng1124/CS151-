import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * 
 * @author Stephanie Cheng
 *
 */
public class CalendarModel 
{
	private GregorianCalendar c = new GregorianCalendar();
	
	private int monthMaxDays;
	private int selectedDate;
	
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private TreeMap<String, ArrayList<Event>> eventMap = new TreeMap<>();
	
	private boolean updated = false;
	
	
	/**
	 * Makes a new CalendarModel
	 */
	public CalendarModel() 
	{
		monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDate = c.get(Calendar.DATE);
		loadMemory();
	}
	
	/**
	 * Attaches a ChangeListener to the model
	 * @param listener the ChangeListener
	 */
	public void attach(ChangeListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Notifies all the views of the change by calling stateChanged for all listeners in the arrayList
	 */
	public void update()
	{
		for (ChangeListener listener : listeners) 
		{
			listener.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Gets the current month of the calendar
	 * @return the index of the current month
	 */
	public int getCalMonth()
	{
		return c.get(Calendar.MONTH);
	}
	

	/**
	 * Gets the date of the calendar
	 * @return the date number
	 */
	public int getCalDate()
	{
		return c.get(Calendar.DATE);
	}
	
	/**
	 * Gets the current day of week of the calendar
	 * @return the day of week SMTWTFS
	 */
	public int getCalDayOfWeek()
	{
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Gets the current calendar year
	 * @return the calendar year
	 */
	public int getCalYear()
	{
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * Gets the total days in the current calendar month
	 * @return the maximum days
	 */
	public int getMonthMaxDays()
	{
		return monthMaxDays;
	}
	
	/**
	 * Finds the day of week a day falls on
	 * @param date the date number
	 * @return the day of week it falls on
	 */
	public int findDayOfWeek(int date)
	{
		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), date);
		return temp.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * To go to the next day
	 */
	public void nextDay() 
	{
		selectedDate++;
		
		//go to next month if it goes over max
		if (selectedDate > c.getActualMaximum(Calendar.DAY_OF_MONTH)) 
		{
			nextMonth();
			//start over the date from the first day of the next month
			selectedDate = 1;
		}
		update(); //let the views know of the change
	}
	
	/*
	 * To move back to the previous day
	 */
	public void previousDay() 
	{
		selectedDate--;
		
		//go to previous month if it goes past first date of current month
		if (selectedDate < 1) 
		{
			previousMonth();
			//start over the date from the last day of the next month
			selectedDate = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		update(); //let the views know of the change
	}
	
	/**
	 * Go to the next month
	 */
	public void nextMonth()
	{
		//add 1 to current month of calendar
		c.add(Calendar.MONTH, 1);
		
		//update all variables applicable
		monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		updated = true;
		update(); //notify all the views that current calendar month moved forward
	}
	
	/**
	 * Go to the previous month
	 */
	public void previousMonth()
	{
		//subtract 1 from current month
		c.add(Calendar.MONTH, -1);
		
		//update all variables applicable
		monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		updated = true;
		update(); //notify all the views that current calendar month moved forward
	}
	
	/**
	 * Checks if the calendar was updated or not
	 * @return whether or not it was updated
	 */
	public boolean hasUpdated()
	{
		return updated;
	}
	
	/**
	 * makes it so that the calendar is no longer updated
	 */
	public void unupdated()
	{
		updated = false;
	}
	
	/**
	 * Sets the selected date to a certain date
	 * @param date the date to select
	 */
	public void setSelectedDate(int date)
	{
		selectedDate = date;
	}
	
	/**
	 * Gets the selected date
	 * @return the selected date
	 */
	public int getSelectedDate()
	{
		return selectedDate;
	}
	
	/**
	 * The event class
	 * @author Stephanie Cheng
	 *
	 */
	private static class Event implements Serializable 
	{
		private static final long serialVersionUID = 1124199607311995L;
		
		private String date;
		private String description;
		private int startHour;
		private int startMin;
		private String startampm;
		
		private int endHour;
		private int endMin;
		private String endampm;

		/**
		 * Creates a new instance of an event
		 * @param date date event occurs
		 * @param description a brief description of the event
		 * @param start the starting time of the event
		 * @param end the ending time of the event
		 */
		private Event(String dateKey, String theDescription, int startingH, int startingM, String ampm1, int endingH, int endingM, String ampm2) 
		{
			date = dateKey;
			description = theDescription;
			
			startHour = startingH;
			startMin = startingM;
			startampm = ampm1;
					
			endHour = endingH;
			endMin = endingM;
			endampm = ampm2;

		}
		
		/**
		 * Turns the event into a string
		 * @return the event entry in readable format
		 */
		public String toString() 
		{	
			
			if(startMin < 10 && endMin < 10)
			{
				return startHour + ":" + "0" +startMin + " " + startampm + "-" + endHour + ":" + "0" + endMin + " " + endampm + ": " + description;
			}
			else if(startMin < 10)
			{
				return startHour + ":" + "0" +startMin + " " + startampm + "-" + endHour + ":" + endMin + " " + endampm + ": " + description;
			}
			else if(endMin < 10)
			{
				return startHour + ":" + "0" +startMin + " " + startampm + "-" + endHour + ":" + endMin + " " + endampm + ": " + description;
			}
			
			return startHour + ":" + startMin + " " + startampm + "-" + endHour + ":" + endMin + " " + endampm + ": " + description;
		}
	}
	
	
	/**
	 * Adds an event to the arraylist of events
	 * @param description the description of the event
	 * @param start the starting time
	 * @param end the ending time
	 */
	public void addEvent(String description, String start, String end)
	{
		String dateKey = (c.get(Calendar.MONTH) + 1) + "/" + selectedDate + "/" + c.get(Calendar.YEAR);
		
		int startHour = Integer.parseInt(start.substring(0, 2));
		int endHour = Integer.parseInt(end.substring(0, 2));
		
		int startMin = Integer.parseInt(start.substring(3, 5));
		int endMin = Integer.parseInt(end.substring(3, 5));
		
		String startampm = start.substring(5);
		String endampm = end.substring(5);
		
		Event e = new Event(dateKey, description, startHour, startMin, startampm, endHour, endMin, endampm);
		
		if (eventMap.containsKey(dateKey)) 
		{
			eventMap.get(dateKey).add(e);
		}
		else
		{
			ArrayList<Event> eventArray = new ArrayList<>();
			eventArray.add(e);
			eventMap.put(dateKey, eventArray);
		}
		
		
	}

	/**
	 * Checks to see if there is an event on a certain day
	 * @param date the date to use as a key to search the keymap
	 * @return whether or not there is an event on that day
	 */
	public boolean hasEvent(String date)
	{
		return eventMap.containsKey(date);
	}
	
	/**
	 * Convert a time to minutes
	 * @param hour 
	 * @param mins
	 * @param ampm whether is am or pm
	 * @return the total amount of minutes
	 */
	private int findTotalMins(int hour, int mins, String ampm)
	{
		if (ampm.equals("am"))
		{
			if(hour == 12)
			{
				return 0;
			}
			return (hour*60) + mins;
		}
		return ((hour + 12)*60) + mins;
	}
	
	/**
	 * Gets the events on a certain day
	 * @param date 
	 * @return the events in a string format
	 */
	public String getEvents(String date) 
	{
		ArrayList<Event> eventArray = eventMap.get(date);
		Collections.sort(eventArray, timeComparator());
		
		String events = "";
		for (Event e : eventArray) 
		{
			events = events + e.toString() + "\n";
		}
		return events;
	}
	
	/**
	 * Checks to see if there is a time conflict
	 * @param time1 start time entered from text field
	 * @param time2 end time entered from text field
	 * @return whether or not there was a time conflict
	 */
	public boolean hasConflict(String time1, String time2)
	{
		//this method assumes that the person types the input into the correct format
		int t1Hour = Integer.parseInt(time1.substring(0, 2));
		int t2Hour = Integer.parseInt(time2.substring(0, 2));
		
		int t1Min = Integer.parseInt(time1.substring(3, 5));
		int t2Min = Integer.parseInt(time2.substring(3, 5));
		
		String t1ampm = time1.substring(5);
		String t2ampm = time2.substring(5);
		
		String dateKey = (getCalMonth() + 1) + "/" + selectedDate + "/" + getCalYear();
		if (!eventMap.containsKey(dateKey)) 
		{
			return false;
		}
		ArrayList<Event> eventArray = eventMap.get(dateKey);
		Collections.sort(eventArray, timeComparator());
		
		int startMins = findTotalMins(t1Hour, t1Min, t1ampm);
		int endMins = findTotalMins(t2Hour, t2Min, t2ampm);
		
		for (Event e : eventArray) 
		{
			int eventStartMins = findTotalMins(e.startHour, e.startMin, e.startampm);
			int eventEndMins = findTotalMins(e.endHour, e.endMin, e.endampm);
			
			if (startMins >= eventStartMins && startMins < eventEndMins) 
			{
				return true;
			} 
			else if (startMins <= eventStartMins && endMins > eventStartMins) 
			{
				return true;
			}
			else if(startMins == eventStartMins || endMins == eventEndMins)
			{
				return true;
			}
		}
		
		return false;
	}
	

	/**
	 * Saves all events to a file
	 */
	public void saveData() 
	{
		if (eventMap.isEmpty()) 
		{
			return;
		}
		try 
		{
			FileOutputStream fileOutput = new FileOutputStream("calendarEvent.ser");
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(eventMap);
			
			objectOutput.close();
			fileOutput.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads all events from "events.ser".
	 */
	@SuppressWarnings("unchecked")
	private void loadMemory() 
	{
		try 
		{
			FileInputStream fIn = new FileInputStream("calendarEvent.ser");
			ObjectInputStream oIn = new ObjectInputStream(fIn);
			
			TreeMap<String, ArrayList<Event>> temp = (TreeMap<String, ArrayList<Event>>) oIn.readObject();
			for (String date : temp.keySet()) 
			{
				if (hasEvent(date)) 
				{
					ArrayList<Event> eventArray = eventMap.get(date);
					eventArray.addAll(temp.get(date));
				} 
				else 
				{
					eventMap.put(date, temp.get(date));
				}
			}
			oIn.close();
			fIn.close();
		} 
		catch (IOException ioe) 
		{
		} 
		catch (ClassNotFoundException c) 
		{
			System.out.println("Class not found");
			c.printStackTrace();
		}
	}
	
	/**
	 * A comparator used to sort events
	 * @return an event comparator
	 */
	private Comparator<Event> timeComparator() 
	{
		return new 
				Comparator<Event>() 
				{
					public int compare(Event e1, Event e2) 
					{
						if(findTotalMins(e1.startHour, e1.startMin, e1.startampm) < findTotalMins(e2.startHour, e2.startMin, e2.startampm))
						{
							return -1;
						}
						else if(findTotalMins(e1.startHour, e1.startMin, e1.startampm) > findTotalMins(e2.startHour, e2.startMin, e2.startampm))
						{
							return 1;
						}
						else
						{
							return 0;
						}
					}
				};
	}

}
