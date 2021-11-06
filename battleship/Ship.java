package battleship;

import java.util.ArrayList;

public class Ship {
    Ships ship;
    String coordinates;
    int shipSize;
    int r, c, r1, c1;
    boolean horizontalLocation;


    Ship(Ships ship, String coordinates) {
        this.ship = ship;
        this.coordinates = coordinates;
        this.shipSize = ship.shipSize;

    }

    static void newShip(Ship ship, Field field) throws RuntimeException {
        ship.addShip();
        field.placeShip(ship.c, ship.c1, ship.r, ship.r1, ship.horizontalLocation);
        field.addForbiddenCells(ship.horizontalLocation, ship.shipSize);
    }

    void addShip() throws RuntimeException {
        getIntCoordinates(this.coordinates);
        if (horizontalLocation && Math.abs(r - r1) + 1 == shipSize);// System.out.println("success");
        else if (!horizontalLocation && Math.abs(c - c1) + 1 == shipSize); //System.out.println("success");
        else throw new RuntimeException("Error! Wrong length of the " + this.ship.shipType + "! Try again:");

    }

    /*получеине целочисленных координат, проверка допустимости значений и расположения на поле*/
    void getIntCoordinates(String coordinates) {
        String[] tempArray;
        if (coordinates.length() <= 7 && coordinates.length() >= 5) tempArray = coordinates.split(" ");
        else throw new RuntimeException("Error! Wrong ship location! Try again:");
        this.c = tempArray[0].charAt(0) - 65;
        this.r = tempArray[0].length() > 2 ? Integer.parseInt(tempArray[0].substring(1, 3)) - 1
                : Integer.parseInt(tempArray[0].substring(1, 2)) - 1;
        this.c1 = tempArray[1].charAt(0) - 65;
        this.r1 = tempArray[1].length() > 2 ? Integer.parseInt(tempArray[1].substring(1, 3)) - 1
                : Integer.parseInt(tempArray[1].substring(1, 2)) - 1;
        //System.out.println(c + " " + r + " and " + c1 + " " + r1);
        if (c > 9 || r > 9 || c < 0 || r < 0 || c1 > 9 || r1 > 9 || c1 < 0 || r1 < 0)
            throw new RuntimeException("Error! Wrong ship location! Try again:");
        if (c == c1 && r != r1) this.horizontalLocation = true;
        else if (r == r1 && c != c1) this.horizontalLocation = false;
        else
            throw new RuntimeException("Error! Wrong ship location! Try again:"); // бросить исключение
      //  System.out.println("horizontal location: " + this.horizontalLocation);
    }
}

enum Ships {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);
    String shipType;
    int shipSize;


    Ships(String shipType, int shipSize) {
        this.shipSize = shipSize;
        this.shipType = shipType;
    }
}