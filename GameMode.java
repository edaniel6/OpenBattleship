

public class GameMode{

    final static int BATTLESHIP = 5;    
    

    
    public static void main (String args[])
    {        
    	MainGUI gameBoard = new MainGUI();       
        
    	gameBoard.eraseBoard();        

        for (int i = 2; i <= BATTLESHIP; i++)
        {
        	gameBoard.populateBoard(i);
        }                      
    }   
}    
