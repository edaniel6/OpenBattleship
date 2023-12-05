import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame
{    

	private static final long serialVersionUID = 1L;

	// array representing the playing grid of 8 rows by 8 columns
	BoardCell panel[][] = new BoardCell[8][8];
    
    // array representing all possible guesses
    JButton button[][] = new JButton[8][8];
    
    //InfoPanel to display the game play information
    BottomMenu info;
    
    //question dialog displayed at the end of a game
    JFrame playAgain;
    
    //Color representing the default color of each button
    Color defaultColor;
    
    //Integer representing the number of guesses made
    int guesses = 0;
    
    //length of the ship
    static int length;    
    
    //array of integers used to store locations of the ships
    static int board[][] = new int[8][8]; 
    
    // Clears the play area for a new game.
    public void eraseBoard()
    {
        guesses = 0;
        for (int x1 = 0; x1 < 8; x1++)
        {
            for (int y1 = 0; y1 < 8; y1++)
            {
                board[x1][y1] = 0;  //erases the placement of the ships
                button[x1][y1].setBackground(defaultColor);//sets the buttons to their default color
            }            
        }
        
        info.guesses.setText("Guesses: " + guesses);//displays the number of guesses
    }
    
    //display a new JFrame asking the user if they wish to play another game.
    public void playAgain()
    {
        playAgain = new JFrame();
        playAgain.setLayout(new FlowLayout());
        playAgain.setVisible(true);
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        JLabel playAgainLabel = new JLabel("Would you like to play again?");
        playAgain.add(playAgainLabel);
        playAgain.add(yes);
        playAgain.add(no);
        no.addActionListener(new ExitGame());
        yes.addActionListener(new PlayAgain());
        playAgain.setTitle("Play Again?");
        playAgain.setDefaultCloseOperation(EXIT_ON_CLOSE);        
        playAgain.pack();       
    }        
    
    //class Constructor

    public MainGUI()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(425,545));
        //setResizable(false);
        setLayout(new FlowLayout(1,0,0));
        setVisible(true);
        setTitle("Battleship"); 
        setResizable(false);
        
        //populates the board with panels with JButtons on them
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {                
                panel[r][c] = new BoardCell();
                button[r][c] = new JButton();
                panel[r][c].setLayout(new FlowLayout());
                button[r][c].setPreferredSize(new Dimension(48,48));
                button[r][c].addActionListener(new ButtonPressed(r,c));
                panel[r][c].add(button[r][c]);
                add(panel[r][c]);
            }
        }              
        
        defaultColor = button[1][1].getBackground();//set the default Color of the JButtons        
        info = new BottomMenu();           
        add(info);//adds the InfoPanel to the bottom of the JFrame        
        pack();      
    } 
    
    //Class to be called when the buttons are pressed
    public class ButtonPressed implements ActionListener
    {   
        int r;//integer to store the value of which row the button that was pressed is in
        int c;//integer to store the value of which column the button that was presses is in
        

        public ButtonPressed(int row, int column)
        {
            r = row;
            c = column;
        }
        
        //Checks if the guess was a hit or miss.  Displays the result on the JFrame.
     
        public void actionPerformed(ActionEvent evt)
        {
            //if the guess was a miss            
            if (board[r][c] == 0)
            {
                button[r][c].setBackground(Color.black);
                board[r][c] = 0;
            }
            
            //mine sweeper hit
            if (board[r][c] == 2)
            {
                button[r][c].setBackground(Color.green);
                board[r][c] = -1;
            }
            
            //frigate hit
            if (board[r][c] == 3)
            {
                button[r][c].setBackground(Color.blue);
                board[r][c] = -1;
            }
            
            //cruiser hit
            if (board[r][c] == 4)
            {
                button[r][c].setBackground(Color.red);
                board[r][c] = -1;
            }
            
            //battleship hit
            if (board[r][c] == 5)
            {
                button[r][c].setBackground(Color.yellow);
                board[r][c] = -1;
            }
            
            guesses++;
            info.guesses.setText("Guesses: " + guesses);     
            
            if (isGameOver())
            {
            	playAgain();
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

    public class PlayAgain implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            eraseBoard();
            for (int i = 2; i <= 5; i++)
            {
            	drawBoard(i);
            }
            
            playAgain.setVisible(false);//hides the window asking the user to play again           
        }
    }
    
    //checks if all of the ships have been hit
    public boolean isGameOver()
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
    
    //fill the board randomly with the 4 ships
    public static void drawBoard(int lengthOfShip)
    {
        //randomly placing the ships
        Random random = new Random();
        
        //used to tell the loop whether to keep trying to place the ship or not
        boolean cont = false;
        
        //decide if the ship should go backwards or forwards
        boolean orientation = false;
        
        //decide if the ship should be placed horizontally or vertically
        boolean direction;
        
        //integer representing the potential x value of the ship position
        int x;
        
        //integer representing the potential y value of the ship position
        int y;
        
        //integer representing if a specific square is empty or not
        boolean emptySquare = true;
        
        length = lengthOfShip;
        
        while (!cont)
        {    
            emptySquare = true;        
        
            orientation = random.nextBoolean();
            direction = random.nextBoolean();
        
            x = random.nextInt(8);
            y = random.nextInt(8);            
       
            //vertical
            if (orientation)
            {            
                //placed to the right
                if (direction)
                {
                
                    //both points are one the board
                    if (y + length <= 7)
                    {                  
                        for (int i = y; i < y + length; i++)
                        {
                            //square is already occupied
                            if (board[x][i] != 0)
                            {
                                emptySquare = false;
                            }
                        }  
                    
                        //ship can be placed here
                        if (emptySquare)
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
                                emptySquare = false;
                            }
                        }
                    
                        //ship can be placed here
                        if (emptySquare)
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
        
            //horizontal
            if (!orientation)
            {
                //placed upward
                if (!direction)
                {           
                    //both points are one the board
                    if (x - length >= 0)
                    {                                                    
                        for (int i = x; i > x - length; i--)
                        {
                            //square is already occupied
                            if (board[i][y] != 0)
                            {
                                emptySquare = false;
                            }
                            
                        }
                        
                        //ship can be placed here
                        if (emptySquare)
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
                                emptySquare = false;
                            }
                            
                        }
                        
                        //ship can be placed here
                        if (emptySquare)
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
        }           
    }    
}

