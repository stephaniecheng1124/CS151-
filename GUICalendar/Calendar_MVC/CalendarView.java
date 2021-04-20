import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * 
 * @author Stephanie Cheng
 *
 */
public class CalendarView implements ChangeListener
{
	private MONTHS[]monthArray = MONTHS.values();
    private DAYS[] dayArray = DAYS.values();
    
    private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
    
    private int monthMaxDays;
    private int monthFirstDay;
    private int bordered;
    
    private int selectedDate;
    
    private JFrame frame = new JFrame("SimpleCalendar");
    private JPanel leftContainer = new JPanel();
    private JPanel rightContainer = new JPanel();
	private JPanel numberPanel = new JPanel();
	private JLabel monthYearLabel = new JLabel();
	private JLabel SMTWTFS = new JLabel();
	private JLabel dayDateLabel = new JLabel();
	private JTextPane eventsPane = new JTextPane();
	
	private JButton nextDay;
	private JButton prevDay;
	private JButton nextMonth;
	private JButton prevMonth;
	private JButton createButton;
	private JButton quitButton;
	
	
    
    private CalendarModel cModel;
	
	/**
	 * Makes a new calendar view
	 * @param model the model that the view is associated with 
	 */
	public CalendarView(CalendarModel model) 
	{
		//make no date be selected by giving it an impossible number
		bordered = -1;
		
		frame.setSize(700, 270);
		cModel = model;
		monthMaxDays = cModel.getMonthMaxDays();
		
		//initialize all buttons
		nextMonth = new JButton("Month>");
		prevMonth = new JButton("<Month");
		nextDay = new JButton(">");
		prevDay = new JButton("<");
		createButton = new JButton("Create");
		createButton.setBackground(Color.GREEN);
		createButton.setFont(new Font("newFont", Font.BOLD, 18));
		
		quitButton = new JButton("Quit");
		quitButton.setBackground(Color.RED);
		quitButton.setForeground(Color.WHITE);
		quitButton.setFont(new Font("newFont", Font.BOLD, 18));
		
		//part where all the dates are on
		numberPanel.setLayout(new GridLayout(0,7));
		
		//box that contains the events
		Dimension dim = new Dimension(300, 140);
		eventsPane.setPreferredSize(dim);
		eventsPane.setEditable(false);
		
		//make the new number buttons
		
		makeDayButtons();
		
		//put in the empty buttons
		for (int i = 1; i < cModel.findDayOfWeek(1); i++) 
		{
			JButton emptyButton = new JButton();
			
			//these buttons will not work
			emptyButton.setEnabled(false);
			numberPanel.add(emptyButton); //add to panel
		}
		
		//add each button from ArrayList to the panel
		for (JButton day : buttonArray) 
		{
			numberPanel.add(day);
		}
		
		//highlightEvents();
		writeEvents(cModel.getSelectedDate());
		borderSelected(cModel.getSelectedDate() - 1);
		
		//start adding actionListeners to the buttons
		
		nextDay.addActionListener(new 
				ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						model.nextDay();
					}
				});
		
		prevDay.addActionListener(new 
				ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						model.previousDay();
						
					}
				});
		
		nextMonth.addActionListener(new 
				ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						cModel.nextMonth();
						createButton.setEnabled(false);
						eventsPane.setText("");
						
						//can't switch to next date unless you select one
						nextDay.setEnabled(false);
						prevDay.setEnabled(false);
					}
				});
		
		prevMonth.addActionListener(new 
				ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						cModel.previousMonth();
						createButton.setEnabled(false);
						eventsPane.setText("");
						
						//can't switch to next date unless you select one
						nextDay.setEnabled(false);
						prevDay.setEnabled(false);
						
					}
				});
		
		createButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				showEventWindow();
			}
		});
		
		quitButton.addActionListener(new 
				ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						//save everything and exit screen
						cModel.saveData();
						System.exit(0);
					}
				});
		
		
		leftContainer.setLayout(new BorderLayout());
		monthYearLabel.setText(monthArray[cModel.getCalMonth()] + " " + model.getCalYear());;
		SMTWTFS.setText("      S         M          T          W          T          F          S");
		
		JPanel monthYearSMTWTFS = new JPanel();
		monthYearSMTWTFS.setLayout(new BorderLayout());
		monthYearSMTWTFS.add(monthYearLabel,BorderLayout.NORTH);
		monthYearSMTWTFS.add(SMTWTFS,BorderLayout.SOUTH);
		
		JPanel createAndNextDays = new JPanel();
		createAndNextDays.add(createButton);
		createAndNextDays.add(prevDay);
		createAndNextDays.add(nextDay);
		
		leftContainer.add(createAndNextDays, BorderLayout.NORTH);
		leftContainer.add(monthYearSMTWTFS, BorderLayout.CENTER);
		leftContainer.add(numberPanel, BorderLayout.SOUTH);
		
		
		rightContainer.setLayout(new BorderLayout());
		JScrollPane eventScroller = new JScrollPane(eventsPane);
		eventScroller.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JPanel nextMonthsAndQuit = new JPanel();
		nextMonthsAndQuit.add(prevMonth);
		nextMonthsAndQuit.add(nextMonth);
		nextMonthsAndQuit.add(quitButton);
		
		dayDateLabel.setText(dayArray[cModel.findDayOfWeek(cModel.getSelectedDate()) - 1] + " " + cModel.getCalMonth() + "/" + cModel.getCalDate());
		
		rightContainer.add(nextMonthsAndQuit, BorderLayout.NORTH);
		rightContainer.add(dayDateLabel, BorderLayout.CENTER);
		rightContainer.add(eventScroller, BorderLayout.SOUTH);
		
		
		//add everything to the frame
		frame.setLayout(new FlowLayout());
		frame.add(leftContainer);
		frame.add(rightContainer);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * redraws and updates the calendar when it senses a change
	 * @param e the event causing change
	 */
	public void stateChanged(ChangeEvent e) 
	{
		//if user has changed clicked the next month, redraw all the buttons
		if (cModel.hasUpdated()) 
		{
			//find and update the new amount of days in the month
			monthMaxDays = cModel.getMonthMaxDays();
			
			//erase buttons from panel
			numberPanel.removeAll();
			
			//delete all the buttons from the array
			buttonArray.clear();
			
			//erase the date since none is selected
			eraseDateLabel();
			
			//update the label for the month/year
			monthYearLabel.setText(monthArray[cModel.getCalMonth()] + " " + cModel.getCalYear());
			
			//make the new number buttons			
			makeDayButtons();
			
			//put in the empty buttons
			for (int i = 1; i < cModel.findDayOfWeek(1); i++) 
			{
				JButton emptyButton = new JButton();
				
				//these buttons will not work
				emptyButton.setEnabled(false);
				numberPanel.add(emptyButton); //add to panel
			}
			
			//add each button from ArrayList to the panel
			for (JButton day : buttonArray) 
			{
				numberPanel.add(day);
			}
			
			//highlightEvents();
			
			
			
			
			//no bordered button
			bordered = -1;
			
			cModel.unupdated();
			frame.pack();
			frame.repaint();
		} 
		else 
		{
			writeEvents(cModel.getSelectedDate());
			changeDateLabel(cModel.getSelectedDate());
			borderSelected(cModel.getSelectedDate() - 1);
		}
	}
	
	/**
	 * Makes each number button and puts it into the array of buttons
	 */
	private void makeDayButtons() 
	{
		
		System.out.println(monthMaxDays);
		for (int i = 1; i <= monthMaxDays; i++) 
		{
			//the first day starts from 1
			final int dayNumber = i;
			
			//create new button
			JButton day = new JButton(Integer.toString(dayNumber));
			day.setBackground(Color.WHITE);
	
			//attach a listener
			day.addActionListener(new 
					ActionListener() 
					{
						public void actionPerformed(ActionEvent arg0) 
						{
							//if button is pressed, highlight it 
							borderSelected(dayNumber -1);
							
							//show the event items in the box on the right
							writeEvents(dayNumber);
							
							changeDateLabel(dayNumber);
							
							//make these buttons available for use
							nextDay.setEnabled(true);
							prevDay.setEnabled(true);
							createButton.setEnabled(true);
						}
					});
			
			//add this button to the day button ArrayList
			buttonArray.add(day);
		}
	}
	

	/**
	 * Makes a red border around selected number
	 * @param toBorder the index of the date number to be selected
	 */
	private void borderSelected(int toBorder) 
	{
		//make the red border for the one that is selected
		Border border = new LineBorder(Color.RED, 5);
		
		//if something else already has a border, remove that border (default was -100)
		if (bordered != -1) 
		{
			buttonArray.get(bordered).setBorder(new JButton().getBorder());
		}
		
		//set the required button to have a thick red border
		buttonArray.get(toBorder).setBorder(border);
		
		//update the index of the bordered button
		bordered = toBorder;
	}
	
	/**
	 * Sets the text in the box to the event on that day
	 * @param dayNumber The say number to write events for
	 */
	private void writeEvents(final int dayNumber) 
	{
		cModel.setSelectedDate(dayNumber);
		
		//add one to month because the first month starts at 0
		int month = cModel.getCalMonth() + 1;   
		int year = cModel.getCalYear();
		String events = "";
		
		String date = (month + "/" + dayNumber + "/" + year);

		if (cModel.hasEvent(date)) 
		{
			events = events + cModel.getEvents(date);
		}
		eventsPane.setText(events);
	}

	/**
	 * To change the date label on the right side of the screen
	 * @param dayNumber the day number to change the label to
	 */
	private void changeDateLabel(final int dayNumber)
	{
		String dayOfWeek = "" + dayArray[cModel.findDayOfWeek(dayNumber) - 1];
		int month = cModel.getCalMonth() + 1;
		
		dayDateLabel.setText(dayOfWeek + " " + month + "/" + dayNumber);
	}

	/**
	 * To get rid of the date label
	 */
	private void eraseDateLabel()
	{
		dayDateLabel.setText("");
	}

	/**
	 * Displays a pop up window when the create button is pressed
	 */
	public void showEventWindow()
	{
		JDialog eventWindow = new JDialog();
		eventWindow.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		//set size and title
		Dimension d = new Dimension(350,200);
		eventWindow.setSize(d);
		eventWindow.setTitle("Make Event");
		
		//make parts of event window
		JTextField theEvent = new JTextField(25);
		Dimension d2 = new Dimension(250,100);
		theEvent.setPreferredSize(d2);
		JLabel eventLabel = new JLabel();
		eventLabel.setText("Event: ");
		
		JTextField start = new JTextField(5);
		JTextField end = new JTextField(5);
		JLabel date = new JLabel();
		date.setText(getEventDate(cModel.getSelectedDate()) + "   ");
		JLabel to = new JLabel("to");
		JButton saveButton = new JButton("Save");
		
		saveButton.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						if (cModel.hasConflict(start.getText(), end.getText())) 
						{
							//make an error message
							JDialog errorPopUp = new JDialog();
							
							errorPopUp.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
							errorPopUp.setLayout(new FlowLayout());
							
							JLabel errorMessage = new JLabel("There is a time conflict. Please enter a different time.");
							
							JButton close = new JButton("Close");
							close.addActionListener(new 
									ActionListener() 
									{
										public void actionPerformed(ActionEvent e) 
										{
											errorPopUp.dispose();
										}
									});
							
							errorPopUp.add(errorMessage);
							errorPopUp.add(close);
							
							errorPopUp.pack();
							errorPopUp.setVisible(true);
						} 
						else 
						{
							eventWindow.dispose();
							cModel.addEvent(theEvent.getText(), start.getText(), end.getText());
							writeEvents(cModel.getSelectedDate());
						}
					}
			
				});
		
		JPanel topPanel = new JPanel();
		topPanel.add(eventLabel);
		topPanel.add(theEvent);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(date);
		bottomPanel.add(start);
		bottomPanel.add(to);
		bottomPanel.add(end);
		bottomPanel.add(saveButton);
		
		eventWindow.setLayout(new BorderLayout());
		eventWindow.add(topPanel,BorderLayout.NORTH);
		eventWindow.add(bottomPanel,BorderLayout.SOUTH);
		
		eventWindow.setVisible(true);
	}
	
	/**
	 * Gets the date of the event by putting in the day number
	 * @param dayNumber the day number
	 * @return the date
	 */
	private String getEventDate(final int dayNumber)
	{
		String dayOfWeek = "" + dayArray[cModel.findDayOfWeek(dayNumber) - 1];
		int month = cModel.getCalMonth() + 1; //first month is 0
		int year = cModel.getCalYear();
		
		return dayOfWeek + " " + month + "/" + dayNumber + "/" + year;
	}
	
}
