package GameModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fleet {

    String[] shipNames = {"Submarine", "Destroyer", "Cruiser", "Battleship", "Aircraft Carrier "};
    int[] shipArray = new int[5];
    int shipLengthCounter;
    int fleetHitCounter = 18;

    public Fleet() {
        shipArray[0] = 2;
        shipArray[1] = 2;
        shipArray[2] = 1;
        shipArray[3] = 1;
        shipArray[4] = 1;
        shipLengthCounter = 0;
    }

    public String getShipName(){
        if(InBounds())
            return shipNames[shipLengthCounter];
        return "Invalid Index";
    }

    public int getNumberOfShips(){
        if(InBounds())
            return shipArray[shipLengthCounter];
        return -1;
    }

    public void incrementShipLengthCounter(){
        shipLengthCounter++;
    }

    public void decrementShipArray(){
        if(InBounds())
            shipArray[shipLengthCounter] = shipArray[shipLengthCounter] - 1;
    }

    public boolean checkActiveFleet()
    {
        fleetHitCounter -= 1;
        if(fleetHitCounter <= 0)
            return false;
        return true;
    }

    public boolean InBounds(){
        return shipLengthCounter < shipArray.length && shipLengthCounter >= 0;
    }
}
