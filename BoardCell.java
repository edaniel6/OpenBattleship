import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

//JPanel that will be placed on each possible guess position
public class BoardCell extends JPanel
{
    
    //Class Constructor
    public BoardCell()
    {
        setPreferredSize(new Dimension(50,50));
        setBackground(Color.black);      
    }
    
    //Paint the panel.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(0,0,49,49);   
    }  
}