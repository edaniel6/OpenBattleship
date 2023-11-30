
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;

//panel for displaying game play information

public class BottomMenu extends JPanel
{
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
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.black);
        g.setColor(Color.black);
        g.drawRect(0,0,399,99);
        g.fillRect(5,15,10,10);
        g.drawString("Miss",20,25);
        g.setColor(Color.green);
        g.fillRect(5,30,10,10);
        g.setColor(Color.white);
        g.drawString("Patrol Boat (2)",20,40);
        g.setColor(Color.blue);
        g.fillRect(5,45,10,10);
        g.setColor(Color.white);
        g.drawString("Submarine (3)",20,55);
        g.setColor(Color.red);
        g.fillRect(5,60,10,10);
        g.setColor(Color.white);
        g.drawString("Battleship (4)",20,70);
        g.setColor(Color.yellow);
        g.fillRect(5,75,10,10);
        g.setColor(Color.white);
        g.drawString("Carrier (5)",20,85);        
    }    
}
