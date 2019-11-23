package GameModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleshipBoard {
    private String player;
    private  int[][] gameBoard = new int[20][20];
    final Integer EMPTY_SPACE = 0;

    public BattleshipBoard(){
        createGame();
    }
    public void createGame(){
        for(int row = 0; row < gameBoard.length; row++)
            for(int col = 0; col < gameBoard[row].length; col++)
                gameBoard[row][col] = EMPTY_SPACE;
    }

}
