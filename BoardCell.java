import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;


public class BoardCell extends JPanel
{
    
    
    public BoardCell()
    {
        setPreferredSize(new Dimension(50,50));
        setBackground(Color.white);      
    }
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawRect(0,0,49,49);   
    }  
}
