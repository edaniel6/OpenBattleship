import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

//JPanel that will be placed on each possible guess position
public class BoardCell extends JPanel
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//class Constructor
    public BoardCell()
    {
        setPreferredSize(new Dimension(50,50));
        setBackground(Color.black);      
    }
    
    //Paint the panel.
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        graphics.setColor(Color.black);
        graphics.drawRect(0,0,49,49);   
    }  
}
