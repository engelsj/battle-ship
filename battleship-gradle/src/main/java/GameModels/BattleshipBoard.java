package GameModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleshipBoard {
    private String player;
    private  int[][] gameBoard = new int[10][10];
    Fleet fleet;
    final Integer EMPTY_SPACE = 0;
    final Integer HIT_SPACE = 1;
    final Integer MISS_SPACE = 2;
    final Integer SHIP_SPACE = 3;

    public BattleshipBoard(){
        createGame();
    }

    // create a new board with a new fleet and make all of the array empty
    public void createGame(){
        fleet = new Fleet();
        for(int row = 0; row < gameBoard.length; row++)
            for(int col = 0; col < gameBoard[row].length; col++)
                gameBoard[row][col] = EMPTY_SPACE;
    }

    // fire a torpedo at a row col
    public int fireTorpedo(int row, int col) {
        if(!inBound(row, col))
            return  0;
        // if we hit a ship decrease the count of active ship tiles in the fleet
        if(gameBoard[row][col] == SHIP_SPACE) {
            gameBoard[row][col] = HIT_SPACE;
            // if the fleet is no longer active the game is now over
            if(!fleet.checkActiveFleet())
                return  4;
            return 1;
        }
        // if we hit an empty space, make it a missed shot
        if(gameBoard[row][col] == EMPTY_SPACE) {
            gameBoard[row][col] = MISS_SPACE;
            return  2;
        }
        // error case when we do not fire upon a valid tile
        else
            return 3;
    }


    public boolean placeShipOnBoard(int shipLength, boolean vertical, int row, int col){
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

    // check if an input is within bounds of the array
    public boolean inBound(int row, int col){
        return !(row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard.length);
    }

    // check if there is space to place the ship vertically
    public boolean checkEmptySpaceVertical(int shipLength, int row, int col){
        for (int i = 0; i < shipLength; i++)
            if( gameBoard[row][col - i] == SHIP_SPACE)
                return false;
            return true;
    }

    // check if there is space to place the ship horizontally
    public boolean checkEmptySpaceHorizontal(int shipLength, int row, int col){
        for (int i = 0; i < shipLength; i++)
            if( gameBoard[row + i][col] == SHIP_SPACE)
                return false;
        return true;
    }

    // first helper function that is called by the main to place a shit
    public Integer placeShip(boolean vertical, int row, int col)
    {
        // if there are ships to place and the ship can be placed
        if(fleet.getNumberOfShips() > 0 && placeShipOnBoard(fleet.getShipLengthCounter() + 1, vertical, row, col - gameBoard.length))
        {
            // if we are on our last ship to place
            if(fleet.getNumberOfShips() - 1 <= 0)
            {
                //  move to the next ship
                fleet.incrementShipLengthCounter();
                // if we are past the last ship then we are done placing ship
                if(fleet.getShipLengthCounter() >= fleet.getShipArray().length)
                   return 2;
                return 1;
            }
            // otherwise just reduce the ship counter
            else {
                fleet.decrementShipArray();
                return 1;
            }
        }
        return  0;
    }






}
