package GameModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleshipBoard {
    private String player;
    private  int[][] gameBoard = new int[10][10];
    final Integer EMPTY_SPACE = 0;
    final Integer HIT_SPACE = 1;
    final Integer MISS_SPACE = 2;
    final Integer SHIP_SPACE = 3;

    public BattleshipBoard(){
        createGame();
    }

    public void createGame(){
        for(int row = 0; row < gameBoard.length; row++)
            for(int col = 0; col < gameBoard[row].length; col++)
                gameBoard[row][col] = EMPTY_SPACE;
    }

    public String fireTorpedo(int row, int col, int action) {
        if(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard.length){
            return "Out of bounds!";
        }
        if(gameBoard[row][col] != 1 || gameBoard[row][col] != 2) {
            if(gameBoard[row][col] == 3) {
                gameBoard[row][col] = HIT_SPACE;
                return "Hit a target at" + row + ", " + col;
            }
            else {
                gameBoard[row][col] = MISS_SPACE;
                return "No target hit at" + row + ", " + col;
            }
        }
        else
            return "This tile has already been fired upon!";

    }


}
