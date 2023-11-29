
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class MainGUI extends JFrame
{    
	BoardCell panel[][] = new BoardCell[8][8];

    JButton button[][] = new JButton[8][8];
    BottomMenu info;
    
    JFrame playAgain;
    
    Color defaultColor;

    int guesses = 0;
    
    static int length;    
    

    static int board[][] = new int[8][8]; 
    
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
    
    
    public void checkForPlayAgain()
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
    

    
    public MainGUI()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(425,545));
        //setResizable(false);
        setLayout(new FlowLayout(1,0,0));
        setVisible(true);
        setTitle("Battleship"); 
        setResizable(false);
        
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
    

    
    public class ButtonPressed implements ActionListener
    {   
        int r;
        int c;
        
 
        
        public ButtonPressed(int row, int column)
        {
            r = row;
            c = column;
        }
        
        /**
         * Checks if the guess was a hit or miss.  Displays the result on the JFrame.
         * 
         * @param   evt the specific ActionEvent that was triggered
         */
        
        public void actionPerformed(ActionEvent evt)
        {
            if (board[r][c] == 0)
            {
                button[r][c].setBackground(Color.black);
                board[r][c] = 0;
            }
            
            if (board[r][c] == 2)
            {
                button[r][c].setBackground(Color.green);
                board[r][c] = -1;
            }
            
            if (board[r][c] == 3)
            {
                button[r][c].setBackground(Color.blue);
                board[r][c] = -1;
            }
            
            if (board[r][c] == 4)
            {
                button[r][c].setBackground(Color.red);
                board[r][c] = -1;
            }
            
            //if the guess hit a battleship
            if (board[r][c] == 5)
            {
                button[r][c].setBackground(Color.yellow);
                board[r][c] = -1;
            }
            
            guesses++;
            info.guesses.setText("Guesses: " + guesses);     
            
            if (checkForGameOver())
            {
                checkForPlayAgain();
            }           
        }           
    }
    
    /**
     * Exits the application.  Implements the ActionListener Interface
     */
    
    public class ExitGame implements ActionListener
    {
        
        /**
         * Exits the application.
         * 
         * @param   evt the specific ActionEvent that was triggered
         */
        
        public void actionPerformed(ActionEvent evt)
        {
            System.exit(0);
        }
    }
    
    /**
     * Calls the methods necessary for replaying the game.  Implements the ActionListener Interface.
     */
    
    public class PlayAgain implements ActionListener
    {
        
        /**
         * Calls the methods necessary for replaying the game.
         * 
         * @param   evt the specific ActionEvent that was triggered
         */
        
        public void actionPerformed(ActionEvent evt)
        {
            eraseBoard();
            for (int i = 2; i <= 5; i++)
            {
                populateBoard(i);
            }
            
            playAgain.setVisible(false);//hides the window asking the user to play again           
        }
    }
    
    /**
     * Checks if all of the ships have been hit.
     * 
     * @return  false if the game is not over, true if the game is over
     */
    
    public boolean checkForGameOver()
    {
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                //if the board doesnt contain a ship or if it contains a ship but the position has already been located
                if (board[row][col] != 0 && board[row][col] != -1)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Fills the board randomly with the 4 ships.
     * 
     * @param   lengthOfShip    the length of the specific ship currently being placed
     */
    
    public static void populateBoard(int lengthOfShip)
    {
        /** instance used for randomly placing the ships*/
        Random random = new Random();
        
        /** used to tell the loop whether to keep trying to place the ship or not*/
        boolean cont = false;
        
        /** used to designate whether the ship should go backwards or forwards*/
        boolean orientation = false;
        
        /** used to designate whether the ship should be placed horizontally or vertically*/
        boolean direction;
        
        /**integer representing the potential x value of the ship position*/
        int x;
        
        /**integer representing the potential y value of the ship position*/
        int y;
        
        /** integer representing whethere a specific square is empty or not*/
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

