package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RuntimeException {
        Ships[] ships = Ships.values();
        int shipsCounter = 0;
        int repeat = 0;
        Scanner scanner = new Scanner(System.in);
        Field fieldPlayer1 = new Field();
        Field fieldPlayer2 = new Field();
        fieldPlayer1.createBattlefield();
        fieldPlayer1.createComparativeBattlefield();
        fieldPlayer1.createFogBattlefield();
        fieldPlayer2.createBattlefield();
        fieldPlayer2.createComparativeBattlefield();
        fieldPlayer2.createFogBattlefield();
        System.out.println("Player 1, place your ships on the game field");
        fieldPlayer1.printBattlefield();
        while (shipsCounter < 5) {
            try {
                if (repeat == 0) {
                    System.out.println("Enter the coordinates of the " + ships[shipsCounter].shipType +
                            " (" + ships[shipsCounter].shipSize + " cells):");
                    repeat++;
                }
                Ship.newShip(new Ship(ships[shipsCounter], scanner.nextLine()), fieldPlayer1);
                shipsCounter++;
                fieldPlayer1.printBattlefield();
                repeat = 0;

            } catch (RuntimeException exc) {
                System.out.println(exc.getLocalizedMessage());
            }
        }

        shipsCounter = 0;

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.println("Player 2, place your ships to the game field");
        while (shipsCounter < 5) {
            try {
                if (repeat == 0) {
                    System.out.println("Enter the coordinates of the " + ships[shipsCounter].shipType +
                            " (" + ships[shipsCounter].shipSize + " cells):");
                    repeat++;
                }
                Ship.newShip(new Ship(ships[shipsCounter], scanner.nextLine()), fieldPlayer2);
                shipsCounter++;
                fieldPlayer2.printBattlefield();
                repeat = 0;

            } catch (RuntimeException exc) {
                System.out.println(exc.getLocalizedMessage());

            }
        }


        promptEnterKey();

        while (fieldPlayer1.isAnyAlive() && fieldPlayer2.isAnyAlive())  {

            while (true) {
                try {
                    fieldPlayer2.printFogBattlefield();
                    System.out.println("---------------------");
                    fieldPlayer1.printBattlefield();
                    System.out.println("Player 1, it's your turn:");
                    String shot = scanner.nextLine();
                    String status = fieldPlayer2.takeShot(shot);
                    if (status.equals("M")) {
                        System.out.println("You missed!");
                        promptEnterKey();
                        break;
                    } else if (status.equals("X")) {
                        if (!fieldPlayer2.isAnyAlive()) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            break;
                        } else if (fieldPlayer2.isShipSink(shot)) {
                            System.out.println("You sank a ship!");
                            promptEnterKey();
                            break;
                        } else {
                            System.out.println("You hit a ship!");
                            promptEnterKey();
                            break;
                        }
                    }

                } catch (RuntimeException exc) {
                    System.out.println(exc.getLocalizedMessage());
                }
            }
            if (!(fieldPlayer1.isAnyAlive() && fieldPlayer2.isAnyAlive())) break;
            while (true) {
                try {
                    fieldPlayer1.printFogBattlefield();
                    System.out.println("---------------------");
                    fieldPlayer2.printBattlefield();
                    System.out.println("Player 2, it's your turn:");
                    String shot = scanner.nextLine();
                    String status = fieldPlayer1.takeShot(shot);
                    if (status.equals("M")) {
                        System.out.println("You missed!");
                        promptEnterKey();
                        break;
                    } else if (status.equals("X")) {
                        if (!fieldPlayer1.isAnyAlive()) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            break;
                        } else if (fieldPlayer1.isShipSink(shot)) {
                            System.out.println("You sank a ship!");
                            promptEnterKey();
                            break;
                        } else {
                            System.out.println("You hit a ship!");
                            promptEnterKey();
                            break;
                        }

                    }

                } catch (RuntimeException exc) {
                    System.out.println(exc.getLocalizedMessage());
                }
            }
            if (!(fieldPlayer1.isAnyAlive() && fieldPlayer2.isAnyAlive())) break;

        }
    }
}
