import GameModels.BattleshipBoard;
import Graphics.GameCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Main extends JFrame implements ActionListener, MouseListener {

    GameCanvas gameCanvas = new GameCanvas();

    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton startButton = new JButton("Start Game");
    JLabel notificationWindow = new JLabel("Welcome to BattleShip");

    BattleshipBoard playerBoard = new BattleshipBoard();
    BattleshipBoard fireBoard = new BattleshipBoard();

    int[] shipArray = new int[5];
    String[] shipNames = {"Submarine", "Destroyer","Cruiser","Battleship","Aircraft Carrier "};
    int shipLengthCounter;

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
        gameCanvas.setFireBoard(fireBoard.getGameBoard());

        // reset ship array
        shipArray[0] = 2;
        shipArray[1] = 2;
        shipArray[2] = 1;
        shipArray[3] = 1;
        shipArray[4] = 1;

        shipLengthCounter = 0;
        gamePhase = 1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row;
        int col;
        if(e.getY() <= 355 && gamePhase == 2) {
            row = getFireCoordinate(e.getX());
            col = getFireCoordinate(e.getY());
            notificationWindow.setText(fireBoard.fireTorpedo(row, col, fireBoard.getHIT_SPACE()));
        }

        else if(e.getY() >= 355 && gamePhase == 1){
            row = getPlayerCoordinate(e.getX());
            col = getPlayerCoordinate(e.getY());
            if(e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1)
                placeShipOnBoard(row, col, false);
            else
                placeShipOnBoard(row, col, true);
        }
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gameCanvas.repaint();
    }

    public void placeShipOnBoard(int row, int col, boolean vertical){

        System.out.println("Ship length counter " + shipLengthCounter);
        System.out.println("Game Phase " + gamePhase);
        if(shipArray[shipLengthCounter] > 0 &&
                playerBoard.placeShip(shipLengthCounter + 1, vertical, row, col - playerBoard.getGameBoard().length))
        {
            notificationWindow.setText( shipNames[shipLengthCounter] + " Placed at X: " + row + " Y: " + col);
            if(shipArray[shipLengthCounter] - 1 <= 0) {
                shipLengthCounter++;
                if(shipLengthCounter >= shipArray.length)
                    gamePhase = 2;
            }
            else
                shipArray[shipLengthCounter] = shipArray[shipLengthCounter] - 1;
        }
        else
            notificationWindow.setText("Unable to Place a Ship at X: " + row + " Y: " + col);

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
}

