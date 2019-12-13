package Graphics;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class GameCanvas extends Canvas {

    // 2d array for the boards
    int[][] playerBoard;
    int[][] fireBoard;

    // width of the tile and border
    final int TILE_WIDTH = 30;
    final int border = 5;
    public GameCanvas(){

    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        // print out the top board
        for(int row = 0; row < fireBoard.length; row++)
            for (int col = 0; col < fireBoard[row].length; col++) {
                if(fireBoard[row][col] == 3)
                    g.setColor(Color.BLUE);
                else
                    g.setColor(checkTileValue(fireBoard[row][col]));
                g.fillRect(row * (TILE_WIDTH + border) + border, col * (TILE_WIDTH + border) + border,
                        TILE_WIDTH, TILE_WIDTH);
            }
        // border between the boards
        g.setColor(Color.GRAY);
        g.fillRect(0, ((TILE_WIDTH + border) * playerBoard.length) + border, getWidth(), border);
        int yOffset = ((TILE_WIDTH + border) * playerBoard.length  - border) + border * 3;

        // print out the bottom board
        for(int row = 0; row < playerBoard.length; row++) {
            for (int col = 0; col < playerBoard[row].length; col++) {
                g.setColor(checkTileValue(playerBoard[row][col]));
                g.fillRect((row * (TILE_WIDTH + border) + border), (col * (TILE_WIDTH + border) + yOffset + border),
                        TILE_WIDTH, TILE_WIDTH);

            }
        }
    }

    // codes for each of the tiles based on color
    public Color checkTileValue(int tileValue){
        if(tileValue == 0)
            return Color.BLUE;
        else if (tileValue == 1)
            return Color.RED;
        else if (tileValue == 2)
            return Color.WHITE;
        else if(tileValue == 3)
            return Color.GRAY;
        else
            return Color.PINK;
    }

}
