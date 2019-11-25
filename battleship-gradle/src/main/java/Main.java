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
            playGame();
        }
    }

    public void playGame(){
        gamePhase = 1;

    }

    public void startNewGame(){
        playerBoard.createGame();
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        fireBoard.createGame();
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gamePhase = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row;
        int col;
        if(e.getY() <= 355 && gamePhase == 1) {
            row = getFireCoordinate(e.getX());
            col = getFireCoordinate(e.getY());
            notificationWindow.setText(fireBoard.fireTorpedo(row, col, fireBoard.getHIT_SPACE()));
        }

        else if(e.getY() >= 355 && gamePhase == 1){
            row = getPlayerCoordinate(e.getX());
            col = getPlayerCoordinate(e.getY());
            notificationWindow.setText(playerBoard.placeShip(3, true, row, col - playerBoard.getGameBoard().length));
        }
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
        gameCanvas.repaint();
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

