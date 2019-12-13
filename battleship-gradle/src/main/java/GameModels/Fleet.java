package GameModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fleet {

    // names of the ships on the board
    String[] shipNames = {"Submarine", "Destroyer", "Cruiser", "Battleship", "Aircraft Carrier "};
    int[] shipArray = new int[5];
    int shipLengthCounter;
    int fleetHitCounter = 18;

    // array of every ship where the index is the length of the ship - 1 and the value is the number of ships of that length
    public Fleet() {
        shipArray[0] = 2;
        shipArray[1] = 2;
        shipArray[2] = 1;
        shipArray[3] = 1;
        shipArray[4] = 1;
        shipLengthCounter = 0;
    }

    // get the name of a ship at an index
    public String getShipName(){
        if(InBounds())
            return shipNames[shipLengthCounter];
        return "Invalid Index";
    }

    // get the number of ships left at that index
    public int getNumberOfShips(){
        if(InBounds())
            return shipArray[shipLengthCounter];
        return -1;
    }

    // increase the index of the ship array to move to the next ship
    public void incrementShipLengthCounter(){
        shipLengthCounter++;
    }

    // reduce the number of ships at an index
    public void decrementShipArray(){
        if(InBounds())
            shipArray[shipLengthCounter] = shipArray[shipLengthCounter] - 1;
    }

    // check if the fleet is not full destroyed
    public boolean checkActiveFleet()
    {
        fleetHitCounter -= 1;
        if(fleetHitCounter <= 0)
            return false;
        return true;
    }

    // check if the shipLength counter is within the bounds of the array
    public boolean InBounds(){
        return shipLengthCounter < shipArray.length && shipLengthCounter >= 0;
    }
}
