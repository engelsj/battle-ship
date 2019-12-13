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

    GameCanvas gameCanvas = new GameCanvas();

    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton startButton = new JButton("Start Game");
    JLabel notificationWindow = new JLabel("Welcome to BattleShip");

    BattleshipBoard playerBoard = new BattleshipBoard();
    BattleshipBoard fireBoard = new BattleshipBoard();

    boolean firedUponBoard[][] = new boolean[10][10];

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            startButton.setText("Reset Game");
            notificationWindow.setText("Welcome to BattleShip");
            startNewGame();
            gameCanvas.repaint();
        }
    }


    public void startNewGame(){
        // reset boards
        playerBoard.createGame();
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        fireBoard.createGame();
        RandomlyPlaceShip();
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gamePhase = 1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row;
        int col;
        int response;
        if(e.getY() <= 355 && gamePhase == 2) {
            row = getFireCoordinate(e.getX());
            col = getFireCoordinate(e.getY());
            response = fireBoard.fireTorpedo(row, col);
            if(response == 0)
                notificationWindow.setText("Out of bounds!");
            else if(response == 1)
                notificationWindow.setText("Hit a target at " + row + ", " + col);
            else if(response == 2)
                notificationWindow.setText("Target missed " + row + ", " + col);
            else if(response == 3)
                notificationWindow.setText("Already Fired Upon!");
            else if(response == 4) {
                notificationWindow.setText("You Destroyed the Fleet!");
                gamePhase = 3;
            }
            randomlyFire();
        }

        else if(e.getY() >= 355 && gamePhase == 1){
            row = getPlayerCoordinate(e.getX());
            col = getPlayerCoordinate(e.getY());
            if(e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
                placeShipOnBoard(row, col, false, playerBoard);
            }
            else {
                placeShipOnBoard(row, col, true, playerBoard);
            }

        }
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gameCanvas.repaint();
    }

    public void placeShipOnBoard(int row, int col, boolean vertical, BattleshipBoard board){


        int response = board.placeShip(vertical, row, col);
        if(response == 2) {
            notificationWindow.setText("Move to Fire Phase!");
            gamePhase = 2;
        }
        else if(response == 1)
            notificationWindow.setText("Ship Placed at " + row + " " + col);
        else if(response == 0)
            notificationWindow.setText("Unable to Place a Ship Here");
    }

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

    public void RandomlyPlaceShip()
    {
        int response = 0;
        int randomRow;
        int randomCol;
        boolean randomDirection;
        while(response != 2) {
            randomRow = getRandomCoordinate();
            randomCol = getRandomCoordinate();
            randomDirection = getRandomBoolean();
            response = fireBoard.placeShip(randomDirection, randomRow, randomCol + 10);
        }
    }

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
            if(!firedUponBoard[randomRow][randomCol]) {
                firedUponBoard[randomRow][randomCol] = true;
                response =  playerBoard.fireTorpedo(randomRow, randomCol);
                shotFired = true;
                if(response == 4)
                {
                    notificationWindow.setText("Your Fleet is Destroyed!");
                    gamePhase = 3;
                }
            }
        }
    }

    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    public int getRandomCoordinate(){
        return (int)(Math.random() * (10));
    }

}

