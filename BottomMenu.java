
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

//panel for displaying game play information

public class BottomMenu extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//number of guesses made
    int numberOfGuesses = 0;
    
    //JLabel containing the integer numberOfGuesses
    JLabel guesses;

    //Class Constructor.
    public BottomMenu()
    {        
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(400,100));        
        guesses = new JLabel("Guesses: " + numberOfGuesses);
        guesses.setForeground(Color.red);
        add(guesses);       
    }
    
    //panel with the necessary information
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        setBackground(Color.black);
        graphics.setColor(Color.black);
        graphics.drawRect(0,0,399,99);
        graphics.fillRect(5,15,10,10);
        graphics.drawString("Miss",20,25);
        graphics.setColor(Color.green);
        graphics.fillRect(5,30,10,10);
        graphics.setColor(Color.white);
        graphics.drawString("Patrol Boat (2)",20,40);
        graphics.setColor(Color.blue);
        graphics.fillRect(5,45,10,10);
        graphics.setColor(Color.white);
        graphics.drawString("Submarine (3)",20,55);
        graphics.setColor(Color.red);
        graphics.fillRect(5,60,10,10);
        graphics.setColor(Color.white);
        graphics.drawString("Battleship (4)",20,70);
        graphics.setColor(Color.yellow);
        graphics.fillRect(5,75,10,10);
        graphics.setColor(Color.white);
        graphics.drawString("Carrier (5)",20,85);        
    }    
}
