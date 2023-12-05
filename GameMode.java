
//main method and calls to methods to initialize the game
public class GameMode{

    //the length of the longest ship
    final static int BATTLESHIP = 5;    
    
    // main method, initializes the game
    public static void main (String args[])
    {        
    	MainGUI gameBoard = new MainGUI();       
        
    	gameBoard.eraseBoard();        

        for (int i = 2; i <= BATTLESHIP; i++)
        {
        	MainGUI.drawBoard(i);
        }                      
    }   
}    
