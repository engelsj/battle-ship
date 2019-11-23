package Graphics;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class GameCanvas extends Canvas {

    int[][] playerBoard;
    int[][] fireBoard;
    final int TILE_WIDTH = 30;
    final int border = 5;
    public GameCanvas(){

    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        g.setColor(Color.blue);
        for(int row = 0; row < playerBoard.length; row++)
            for (int col = 0; col < playerBoard[row].length; col++)
                g.fillRect(row * (TILE_WIDTH + border) + border, col * (TILE_WIDTH + border) + border,
                        TILE_WIDTH, TILE_WIDTH);


        g.setColor(Color.GRAY);
        g.fillRect(0, ((TILE_WIDTH + border) * playerBoard.length) + border, getWidth(), border);
        int yOffset = ((TILE_WIDTH + border) * playerBoard.length  - border) + border * 3;


        for(int row = 0; row < fireBoard.length; row++) {
            for (int col = 0; col < fireBoard[row].length; col++) {
                g.setColor(Color.BLUE);
                g.fillRect((row * (TILE_WIDTH + border) + border), (col * (TILE_WIDTH + border) + yOffset + border),
                        TILE_WIDTH, TILE_WIDTH);
            }
        }

    }

}
