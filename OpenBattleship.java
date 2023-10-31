//Note This is the basic console implementation, Implementing the Lanterna interface and making it
//function correctly with the GNOME terminal is a work in progress

//CURRENT BUG TO FIX: For some reason certain ship placements are causing an out of bounds of array index error

import java.util.Random;
import java.util.Scanner;


public class OpenBattleship {
    public static void main(String[] args) {

        //create the Player and CPU instances
        SinglePlayer localPlayer = new SinglePlayer();
        CPU cpu = new CPU();


        //create the player and CPUs game boards
        localPlayer.makeBoard();
        cpu.makeBoard();


        //The main gameplay loop
        while (true) {
            //output only the player's board to them
            localPlayer.boardOut();
            localPlayer.attackOut();

            //Uncomment for Debugging purposes
            cpu.boardOut();

            //the attack phase
            localPlayer.move(cpu);

            //The following 2 if statements check if either party lost all ships
            if (cpu.isWin()) {
                System.out.println("You win!");
                break;
            }
            //The cpu attacks the players board
            cpu.move(localPlayer);
            if (localPlayer.isWin()) {
                System.out.println("CPU wins!");
                break;
            }
        }
    }
}



//Player is an abstract class to make multiplayer implementation easier
abstract class Player {
    char[][] board = new char[10][10];
    char[][] attacks = new char[10][10];


    abstract void makeBoard();


    abstract void move(Player opponent);

    //Iterates through a player's game board to check if they are out of ships
    boolean isWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 'S') return false;
            }
        }
        return true;
    }

    //Simple board array output
    void boardOut() {
        System.out.println("Your board:");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        }

        //Shows attempted attacks and hits
        void attackOut()
        {
            System.out.println("\nYour attacks:");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(attacks[i][j] + " ");
                }
                System.out.println();
        }
    }
}


//This is the class that is used to create a player instance for singleplayer game mode
class SinglePlayer extends Player {
    Scanner scanner = new Scanner(System.in);

    //This is an array of the ships the player has
    Ship[] ships = {
            new Ship("Aircraft Carrier", 5),
            new Ship("Destroyer", 2),
            new Ship("Submarine", 3),
            new Ship("Cruiser", 3),
            new Ship("Frigate", 4)
    };

    @Override
    void makeBoard() {
        //creates an empty game board of character '-'
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = '-';
                attacks[i][j] = '-';
            }
        }

        //Iterate over each ship in the ships array
        for (int i = 0; i < ships.length; i++) {

            //Output the board on each loop so the player can visualize placements
            boardOut();

            //The coordinate the player chooses determines where the head of the ship will be
            System.out.println("Place " + ships[i].name + "(" + ships[i].length + " units)");
            System.out.print("Enter X coordinate:");
            int x = scanner.nextInt();
            System.out.print("\nEnter Y coordinate:");
            int y = scanner.nextInt();

            //changes the rotation of the ship
            System.out.print("\nEnter V for vertical or H for horizontal:");
            char rotate = scanner.next().toLowerCase().charAt(0);

            if (!placement(ships[i], x, y, rotate)) {
                System.out.println("Your placement is out of bounds (0-9), or occupied. Try Again!");
                i--;
            }
        }
    }

        //ship placement checker
         private boolean placement(Ship ship, int x, int y, char rotation) {

            // Place the ship
            for (int i = 0; i < ship.length; i++) {

                //This switch manages the rotation of the ship
                switch(rotation)
                {
                    case 'v':
                        int xPlace = x + i;
                        board[xPlace][y] = 'S';
                        break;

                    case 'h':
                        int yPlace = y + i;
                        board[x][yPlace] = 'S';
                        break;

                }

            }
            //returns a true so the caller knows it was successful
            return true;
        }



    //Takes in the opponent as a parameter to get the enemies game board
    @Override
    void move(Player opponent) {
        System.out.print("Enter x and y coordinates for your attack: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();


        //The function that is calling this will loop until the player has performed a valid attack
        if (x < 0 || x >= 10 || y < 0 || y >= 10 || attacks[x][y] != '-') {
            System.out.println("Coordinates out of bounds (0-9), or already attacked. Try again.");
            move(opponent);
            return;
        }
        if (opponent.board[x][y] == 'S') {
            attacks[x][y] = 'H';
            opponent.board[x][y] = 'H';
            System.out.println("Hit!");
        } else {
            attacks[x][y] = 'M';
            System.out.println("Miss!");
        }
    }
}

//CPU class for singleplayer / local game use only!!
class CPU extends Player {
    Random random = new Random();

    //This is an array of the ships the CPU has
    Ship[] ships = {
            new Ship("Aircraft Carrier", 5),
            new Ship("Destroyer", 2),
            new Ship("Submarine", 3),
            new Ship("Cruiser", 3),
            new Ship("Frigate", 4)
    };

    //generates a board with random ship placements
    @Override
    void makeBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = '-';
                attacks[i][j] = '-';
            }
        }
        for (int i = 0; i < ships.length;) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            //This line is randomly choosing orientation
            //The ? operator works kind of like an if statement
            //For more info please google "ternary operator"
            char rotation = random.nextBoolean() ? 'h' : 'v';

            //Keep in mind this algorithm is a hack
            //It is brute forcing cpu ship placements without trying to account for previous placements
            if (placement(ships[i],x , y, rotation))
            {
                i++;
            }
        }
    }


    private boolean placement(Ship ship, int x, int y, char rotation) {


        for (int i = 0; i < ship.length; i++) {

            int chosenX = x + (rotation == 'h' ? i : 0);
            int chosenY = y + (rotation == 'v' ? i : 0);

            //check if any of the spots it's going to be on are occupied or out of bounds
            if (chosenX < 0 || chosenX >= 10 || chosenY < 0 || chosenY >= 10 || board[chosenX][chosenY] == 'S') {
                return false;
            }
        }

        // Place the ship
        for (int i = 0; i < ships.length; i++) {

            switch(rotation)
            {
                case 'v':
                    int xPlace = x + i;
                    board[xPlace][y] = 'S';
                    break;

                case 'h':
                    int yPlace = y + i;
                    board[x][yPlace] = 'S';
                    break;

            }

        }
        //returns a true so the caller knows it was successful
        return true;
    }


    //AI will attack random coordinates each turn
    //TODO: AI should only attack non-attacked coordinates, and attack coordinates next to previous hits
    @Override
    void move(Player opponent) {
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        if (attacks[x][y] != '-') {
            move(opponent);
            return;
        }
        if (opponent.board[x][y] == 'S') {
            attacks[x][y] = 'H';
            opponent.board[x][y] = 'H';
            System.out.println("CPU hit!");
        } else {
            attacks[x][y] = 'M';
            System.out.println("CPU missed!");
        }
    }
}


//Basis for all the ship types
 class Ship {
    String name;
    int length;


    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }
}
