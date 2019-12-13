import GameModels.BattleshipBoard;
import GameModels.Fleet;
import Graphics.GameCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JFrame implements ActionListener, MouseListener {

    // canvas which we will draw on
    GameCanvas gameCanvas = new GameCanvas();

    // panel that will hold the gameboards
    JPanel mainPanel = new JPanel();

    // panel that will hold the buttons
    JPanel buttonPanel = new JPanel();

    // buttons and labels that will be displayed
    JButton startButton = new JButton("Start Game");
    JLabel notificationWindow = new JLabel("Welcome to BattleShip");

    // the players board where they will place ships and the enemy will fire
    BattleshipBoard playerBoard = new BattleshipBoard();

    // the enemy's board which the player will fire upon and the enemy place ships
    BattleshipBoard fireBoard = new BattleshipBoard();

    // a board to control where the enemy has already fired upon
    boolean firedUponBoard[][];

    // the phase of the game
    // gamePhase 1 = placing ships
    // gamePhase 2 = firing torpedo
    // gamePhase 3 = end of game, someone one

    int gamePhase = 0;

    public Main(){

        // set up window
        setSize(355,775);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Battleship Game");
        setLocationRelativeTo(null);
        gameCanvas.setFireBoard(new int[10][10]);
        gameCanvas.setPlayerBoard(new int[10][10]);
        setResizable(false);

        // set up button
        startButton.addActionListener(this);
        buttonPanel.add(startButton);
        buttonPanel.add(notificationWindow);

        // set up panels
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(gameCanvas, BorderLayout.CENTER);
        gameCanvas.addMouseListener(this);
        addMouseListener(this);

        add(mainPanel);
        setVisible(true);
    }


    public static  void  main(String[] args){
        new Main();
    }

    // controls the buttons that the user clicks on
    @Override
    public void actionPerformed(ActionEvent e) {
        // resets the game
        if(e.getSource() == startButton){
            startButton.setText("Reset Game");
            notificationWindow.setText("Welcome to BattleShip");
            startNewGame();
            gameCanvas.repaint();
        }
    }

    // resets/creates a new game
    public void startNewGame(){
        // reset boards
        playerBoard.createGame();
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        fireBoard.createGame();
        // randomly place new ships
        firedUponBoard = new boolean[10][10];
        RandomlyPlaceShip();
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        // reset the game phase
        gamePhase = 1;
    }

    // controls what happens when a click is made
    @Override
    public void mouseClicked(MouseEvent e) {
        // row and column of the click
        int row;
        int col;
        int response;
        // if the click is made during the fire phase, fire a torpedo
        if(e.getY() <= 355 && gamePhase == 2) {
            // get the row and column of where the user clicks
            row = getFireCoordinate(e.getX());
            col = getFireCoordinate(e.getY());
            // print out what happens when the play fires
            response = fireBoard.fireTorpedo(row, col);
            if(response == 0)
                notificationWindow.setText("Out of bounds!");
            // if the player makes a valid shot, the enemy should fire back
            else if(response == 1) {
                notificationWindow.setText("Hit a target at " + row + ", " + col);
                randomlyFire();
            }
            else if(response == 2) {
                notificationWindow.setText("Target missed " + row + ", " + col);
                randomlyFire();
            }
            else if(response == 3)
                notificationWindow.setText("Already Fired Upon!");
            // once all of the ships are destroyed, the game is over
            else if(response == 4) {
                notificationWindow.setText("You Destroyed the Fleet!");
                gamePhase = 3;
            }
        }

        // if the player is clicking on their board and it is game phase 1, they can place ships
        else if(e.getY() >= 355 && gamePhase == 1){
            // get the rows and column of the click
            row = getPlayerCoordinate(e.getX());
            col = getPlayerCoordinate(e.getY());
            // check if they are placing it vertical or horizontal
            if(e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
                placeShipOnBoard(row, col, false, playerBoard);
            }
            else {
                placeShipOnBoard(row, col, true, playerBoard);
            }

        }
        // update the game boards to display the changes that were made during the click
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gameCanvas.repaint();
    }

    // helper function that checks what is going on when a ship is placed
    public void placeShipOnBoard(int row, int col, boolean vertical, BattleshipBoard board){
        int response = board.placeShip(vertical, row, col);
        // if all of the ships have been placed, move to fire
        if(response == 2) {
            notificationWindow.setText("Move to Fire Phase!");
            gamePhase = 2;
        }
        // if it was a valid placement tell the user
        else if(response == 1)
            notificationWindow.setText("Ship Placed at " + row + " " + col);
        else if(response == 0)
            notificationWindow.setText("Unable to Place a Ship Here");
    }

    // Does some math to get the board coordinates to the row,col of the array
    public int getFireCoordinate(int cord){
        return cord / (gameCanvas.getTILE_WIDTH() + gameCanvas.getBorder());
    }

    public int getPlayerCoordinate(int cord){
        return (cord - gameCanvas.getBorder() * 2) / (gameCanvas.getTILE_WIDTH() + (gameCanvas.getBorder()));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // randomly places ships on the board
    public void RandomlyPlaceShip()
    {
        int response = 0;
        int randomRow;
        int randomCol;
        boolean randomDirection;
        // get a random row and col and see if we can place a ship there
        while(response != 2) {
            randomRow = getRandomCoordinate();
            randomCol = getRandomCoordinate();
            randomDirection = getRandomBoolean();
            response = fireBoard.placeShip(randomDirection, randomRow, randomCol + 10);
        }
    }

    // randomly fires at a random row and col
    public void randomlyFire()
    {
        boolean shotFired = false;
        int randomRow;
        int randomCol;
        int response;
        while(!shotFired)
        {
            randomRow = getRandomCoordinate();
            randomCol = getRandomCoordinate();
            // use a 2d array to make sure we dont fire at the same index twice
            if(!firedUponBoard[randomRow][randomCol]) {
                firedUponBoard[randomRow][randomCol] = true;
                response =  playerBoard.fireTorpedo(randomRow, randomCol);
                shotFired = true;
                // if the enemy hits every ship, end the game
                if(response == 4)
                {
                    notificationWindow.setText("Your Fleet is Destroyed!");
                    gamePhase = 3;
                }
            }
        }
    }

    // help function to get a random bool
    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    // helper function to get a random coordinate
    public int getRandomCoordinate(){
        return (int)(Math.random() * (10));
    }

}

