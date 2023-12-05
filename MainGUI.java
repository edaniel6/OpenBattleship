import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;


public class MainGUI extends JFrame
{    
    private static final long serialVersionUID = 1L;

    Color defaultColor;
    //menu to display the game play information
    BottomMenu info;
    //question asked at the end of a game
    JFrame askToPlay;
    //number of guesses made
    int moves = 0;
    // array representing all possible guesses
    JButton button[][] = new JButton[8][8];
    // array that stores the grid for the board 
    BoardCell cell[][] = new BoardCell[8][8]; 
    //array of integers used to store locations of the ships
    static int board[][] = new int[8][8]; 
    //length of the ship
    static int length;   
    
    //fill the board randomly 
    public static void drawBoard(int shipLength)
    {
        //randomly placing the ships
        Random random = new Random();
        
        //place the ship or not
        boolean placing = false;
        
        //place the ship backwards or forward
        boolean location = false;
        
        //place the ship horizontally or vertically
        boolean trajectory;
        
        int x;
        int y;
        
        //is a specific square is empty or not
        boolean emptyCell = true;
        
        length = shipLength;
        
        while (!placing)
        {    
        	emptyCell = true;        
        
            location = random.nextBoolean();
            trajectory = random.nextBoolean();
        
            x = random.nextInt(8);
            y = random.nextInt(8);            
       
            //horizontal
            if (!location)
            {
                //placed upward
                if (!trajectory)
                {           
                    //both points are one the board
                    if (x - length >= 0)
                    {                                                    
                        for (int i = x; i > x - length; i--)
                        {
                            //square is already occupied
                            if (board[i][y] != 0)
                            {
                            	emptyCell = false;
                            }
                            
                        }
                        
                        //ship can be placed here
                        if (emptyCell)
                        {
                            for (int i = x; i > x - length; i--)
                            {
                                board[i][y] = length;                              
                            
                            }
                            return;
                        }
                    }
                }
                
                //placed downward
                else
                {
                    if (x + length <= 7)
                    {
                        for (int i = x; i < x + length; i++)
                        {   
                            //square is already occupied
                            if (board[i][y] != 0)
                            {
                            	emptyCell = false;
                            }
                            
                        }
                        
                        //ship can be placed here
                        if (emptyCell)
                        {
                            for (int i = x; i < x + length; i++)
                            {
                            
                                board[i][y] = length;                         
                            }
                            return;
                        }
                    }
                }
            }
            //vertical
            if (location)
            {            
                //placed to the right
                if (trajectory)
                {
                
                    //both points are one the board
                    if (y + length <= 7)
                    {                  
                        for (int i = y; i < y + length; i++)
                        {
                            //square is already occupied
                            if (board[x][i] != 0)
                            {
                            	emptyCell = false;
                            }
                        }  
                    
                        //ship can be placed here
                        if (emptyCell)
                        {
                            for (int i = y; i < y + length; i++)
                            {
                                board[x][i] = length;
                            }                           
                            return;
                        }
                    }
                }
                
                //placed to the left
                else
                {
                    if (y - length >= 0)
                    {
                        for (int i = y; i > y - length; i--)
                        {
                            //square is already occupied
                            if (board[x][i] != 0)
                            {
                            	emptyCell = false;
                            }
                        }
                    
                        //ship can be placed here
                        if (emptyCell)
                        {
                            for (int i = y; i > y - length; i--)
                            {
                            
                                board[x][i] = length;                                           
                                                             
                            }
                            return;
                        }
                    }
                }
                    
            }            
        

        }           
    }    

    // clears the play area for a new game
    public void clearBoard()
    {
    	moves = 0;
        for (int xNew = 0; xNew < 8; xNew++)
        {
            for (int yNew = 0; yNew < 8; yNew++)
            {
		//erase the placement of the ships
                board[xNew][yNew] = 0;  
		//set the buttons to their default color
                button[xNew][yNew].setBackground(defaultColor);
            }            
        }
        
        info.moves.setText("Shots Fired: " + moves);//displays the number of guesses
    }
    
    //display a new JFrame asking the user if they wish to play another game.
    public void askToPlay()
    {
    	askToPlay = new JFrame();
    	askToPlay.setLayout(new FlowLayout());
    	askToPlay.setVisible(true);
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        JLabel askToPlayLabel = new JLabel("Do you want to play again?");
        askToPlay.add(askToPlayLabel);
        askToPlay.add(yes);
        askToPlay.add(no);
        no.addActionListener(new ExitGame());
        yes.addActionListener(new askToPlay());
        askToPlay.setTitle("Play Again?");
        askToPlay.setDefaultCloseOperation(EXIT_ON_CLOSE);        
        askToPlay.pack();       
    }        
    
    //constructor
    public MainGUI()
    {
    	//name of the window
        setTitle("Open BattleShip"); 
        //window dimensions
        setPreferredSize(new Dimension(425,545));
        //layout of panels
        setLayout(new FlowLayout(1,0,0));
        setVisible(true);
        //function to stop the user from changing dimensions
        setResizable(false);
        //function to close the program when we close the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //populate the board 
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {                
            	cell[r][c] = new BoardCell();
                cell[r][c].setLayout(new FlowLayout());
                button[r][c] = new JButton();
                button[r][c].setPreferredSize(new Dimension(48,48));
                button[r][c].addActionListener(new CellSelected(r,c));
                cell[r][c].add(button[r][c]);
                add(cell[r][c]);
            }
        }              
        
        defaultColor = button[1][1].getBackground();//set the default Color of the JButtons        
        info = new BottomMenu();           
        add(info);//add the InfoPanel to the bottom of the JFrame        
        pack();      
    } 
    
    //class to be called when the buttons are pressed
    public class CellSelected implements ActionListener
    {   
        int r;
        int c;
        

        public CellSelected(int row, int column)
        {
            r = row;
            c = column;
        }
        
        //check if hit or miss, display the result
     
        public void actionPerformed(ActionEvent evt)
        {
            //if the guess was a miss            
            if (board[r][c] == 0)
            {
                button[r][c].setBackground(Color.black);
                board[r][c] = 0;
            }
            
            //patrol board hit
            if (board[r][c] == 2)
            {
                button[r][c].setBackground(Color.yellow);
                board[r][c] = -1;
            }
            
            //submarine hit
            if (board[r][c] == 3)
            {
                button[r][c].setBackground(Color.blue);
                board[r][c] = -1;
            }
            
            //battleship hit
            if (board[r][c] == 4)
            {
                button[r][c].setBackground(Color.red);
                board[r][c] = -1;
            }
            
            //carrier hit
            if (board[r][c] == 5)
            {
                button[r][c].setBackground(Color.green);
                board[r][c] = -1;
            }
            
            moves++;
            info.moves.setText("Shots Fired: " + moves);     
            
            if (gameOver())
            {
            	askToPlay();
            }           
        }           
    }
    
    //exit the application
    public class ExitGame implements ActionListener
    {
        
        
        public void actionPerformed(ActionEvent evt)
        {
            System.exit(0);
        }
    }
    
    //methods necessary for replaying the game.

    public class askToPlay implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
        	clearBoard();
            for (int i = 2; i <= 5; i++)
            {
            	drawBoard(i);
            }
            
            askToPlay.setVisible(false);//hides the window asking the user to play again           
        }
    }
    
    //checks if all of the ships have been hit
    public boolean gameOver()
    {
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                //if the board doesn't contain a ship or if it contains a ship but the position has already been located
                if (board[row][col] != 0 && board[row][col] != -1)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
    
