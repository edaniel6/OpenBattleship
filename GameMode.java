
/**
 * Contains the main method and calls methods to initialize the game
 */

public class GameMode{

    /** integer representing the lenght of the longest ship*/
    final static int BATTLESHIP = 5;    
    
    /**
     * Main method, initializes the game.
     * 
     * @param   args    array of strings
     */
    
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