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

    public void createAIBoard(){

    }

    public String fireTorpedo(int row, int col, int action) {
        if(!inBound(row, col))
            return "Out of bounds!";
        if(gameBoard[row][col] != 1 || gameBoard[row][col] != 2) {
            if(gameBoard[row][col] == 3) {
                gameBoard[row][col] = action;
                return "Hit a target at " + row + ", " + col;
            }
            else {
                gameBoard[row][col] = action;
                return "No target hit at " + row + ", " + col;
            }
        }
        else
            return "This tile has already been fired upon!";
    }

    public boolean placeShip(int shipLength, boolean vertical, int row, int col){
        if(canPlaceShip(shipLength, vertical, row, col)){
            if(vertical) {
                for (int i = 0; i < shipLength; i++)
                    gameBoard[row][col - i] = SHIP_SPACE;
                return true;
            }
            else {
                for (int i = 0; i < shipLength; i++)
                    gameBoard[row + i][col] = SHIP_SPACE;
                return true;
            }
        }
        return false;
    }

    public boolean canPlaceShip(int shipLength, boolean vertical, int row ,int col) {

        if(vertical) {
            if(inBound(row, col - shipLength + 1))
                if(checkEmptySpaceVertical(shipLength, row, col))
                    return true;
            return false;
        }
        else if(inBound(row + shipLength - 1, col))
            if(checkEmptySpaceHorizontal(shipLength, row, col))
                return true;
        return false;
    }

    public boolean inBound(int row, int col){
        return !(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard.length);
    }

    public boolean checkEmptySpaceVertical(int shipLength, int row, int col){
        for (int i = 0; i < shipLength; i++)
            if( gameBoard[row][col - i] == SHIP_SPACE)
                return false;
            return true;
    }

    public boolean checkEmptySpaceHorizontal(int shipLength, int row, int col){
        for (int i = 0; i < shipLength; i++)
            if( gameBoard[row + i][col] == SHIP_SPACE)
                return false;
        return true;
    }





}
