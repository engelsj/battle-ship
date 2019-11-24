import GameModels.BattleshipBoard;
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

    BattleshipBoard playerBoard = new BattleshipBoard();
    BattleshipBoard fireBoard = new BattleshipBoard();

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
            startNewGame();
            gameCanvas.repaint();
        }
    }

    public void startNewGame(){
        playerBoard.createGame();
        gameCanvas.setPlayerBoard(playerBoard.getGameBoard());

        fireBoard.createGame();
        gameCanvas.setFireBoard(fireBoard.getGameBoard());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getY() <= 355) {
            System.out.println("Mouse pressed at X:" + e.getX() / (gameCanvas.getTILE_WIDTH() + gameCanvas.getBorder()) +
                    " Y:" + e.getY() / (gameCanvas.getTILE_WIDTH() + gameCanvas.getBorder()));
            playerBoard.fireTorpedo(e.getX() / (gameCanvas.getTILE_WIDTH() + gameCanvas.getBorder()),
                    e.getY() / (gameCanvas.getTILE_WIDTH() + gameCanvas.getBorder()), playerBoard.getHIT_SPACE());
            gameCanvas.setPlayerBoard(playerBoard.getGameBoard());
            gameCanvas.repaint();

        }


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

