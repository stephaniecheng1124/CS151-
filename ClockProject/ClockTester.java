package hw4;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;

/**
   Class for testing a clock icon by updating it each second
*/
public class ClockTester
{
   /**
      Creates a clock and a timer to update the clock every second.
      @param args the argument
   */
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      ClockIcon icon = new ClockIcon(400);
      final JLabel label = new JLabel(icon);


      Container contentPane = frame.getContentPane();
      contentPane.setLayout(new FlowLayout());
      contentPane.add(label);

      //ActionListener to help repaint the clock every second
      ActionListener listener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               label.repaint();
            }
         };

      final int DELAY = 1000; 
      // milliseconds between timer ticks = 1 second
      
      Timer t = new Timer(DELAY, listener);
      t.start();

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}