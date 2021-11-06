package battleship;

import java.util.Arrays;

public class Field {
    String[][] mainBattleField = new String[10][10];
    String[][] comparativeBattleField = new String[10][10];
    String[][] fogBattleField = new String[10][10];
    int counter = 0;
    int shotPoint = 0;
    int totalHealth = 17;
    int c;
    int r;


    void createBattlefield() {
        for (String[] row : this.mainBattleField) {
            Arrays.fill(row, "~");
        }
    }

    void createComparativeBattlefield() {
        for (String[] row : this.comparativeBattleField) {
            Arrays.fill(row, "~");
        }
    }

    void createFogBattlefield() {
        for (String[] row : this.fogBattleField) {
            Arrays.fill(row, "~");
        }
    }

    void printBattlefield() {
        char a = 'A';
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < mainBattleField.length; i++) {
            System.out.print((char) (a + i) + " ");
            for (int j = 0; j < mainBattleField[i].length; j++) {
                System.out.print(mainBattleField[i][j] + " ");
            }
            System.out.println();
        }
    }

    void printFogBattlefield() {
        char a = 'A';
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < fogBattleField.length; i++) {
            System.out.print((char) (a + i) + " ");
            for (int j = 0; j < fogBattleField[i].length; j++) {
                System.out.print(fogBattleField[i][j] + " ");
            }
            System.out.println();
        }
    }

    void placeShip(int c, int c1, int r, int r1, boolean horizontal) throws RuntimeException {
        if (horizontal) {
            if (r < r1) {
                for (int i = r; i <= r1; i++) {
                    if (comparativeBattleField[c][i].equals("O")) {
                        for (i = r; i < r + counter; i++) this.mainBattleField[c][i] = "~";
                        throw new RuntimeException("Error! You placed it " +
                                "too close to another one. Try again:");
                    }
                    mainBattleField[c][i] = "O";
                    counter++;
                }
                counter = 0;
            } else for (int i = r1; i <= r; i++) {
                if (comparativeBattleField[c][i].equals("O")) {
                    for (i = r1; i < r1 + counter; i++) mainBattleField[c][i] = "~";
                    throw new RuntimeException("Error! You placed it " +
                            "too close to another one. Try again:");
                }
                mainBattleField[c][i] = "O";
                counter++;
            }
            counter = 0;
        } else {
            if (c < c1) {
                for (int i = c; i <= c1; i++) {
                    if (comparativeBattleField[i][r].equals("O")) {
                        for (i = c; i <= c + counter; i++) {
                            mainBattleField[i][r] = "~";
                        }
                        throw new RuntimeException("Error! You placed it " +
                                "too close to another one. Try again:");
                    }
                    mainBattleField[i][r] = "O";
                    counter++;
                }
                counter = 0;
            } else for (int i = c1; i <= c; i++) {
                if (comparativeBattleField[i][r].equals("O")) {
                    for (i = c1; i < c1 + counter; i++) {
                        mainBattleField[i][r] = "~";
                    }
                    throw new RuntimeException("Error! You placed it " +
                            "too close to another one. Try again:");
                }
                mainBattleField[i][r] = "O";
                counter++;
            }
            counter = 0;
        }
    }

    void addForbiddenCells(boolean horizontal, int shipSize) {
        int startPoint = 0, endPoint = 0, column = 0, row = 0;
        boolean isFound = false;

        for (int i = 0; i < this.mainBattleField.length; i++) {
            for (int j = 0; j < this.mainBattleField[i].length; j++) {
                if (this.mainBattleField[i][j].equals("O") && !this.comparativeBattleField[i][j].equals("O")) {
                    this.comparativeBattleField[i][j] = "F";
                }
            }
        }
        if (horizontal) {
            for (int i = 0; i < this.comparativeBattleField.length; i++) {
                if (isFound) break;
                for (int j = 0; j < this.comparativeBattleField[i].length; j++) {
                    if (this.comparativeBattleField[i][j].equals("F")) {
                        startPoint = j;
                        column = i;
                        if (startPoint == 0) {
                            shipSize++;
                            endPoint = startPoint + shipSize;
                            isFound = true;
                            break;
                        } else {
                            startPoint -= 1;
                            shipSize += 2;
                            endPoint = startPoint + shipSize;
                            isFound = true;
                            break;
                        }
                    }
                }
            }
            if (endPoint > 9) shipSize--;
            for (int i = startPoint; i < startPoint + shipSize; i++) {
                this.comparativeBattleField[column][i] = "O";
                if (column + 1 < 10) this.comparativeBattleField[column + 1][i] = "O";
                if (column - 1 >= 0) this.comparativeBattleField[column - 1][i] = "O";
            }
        }

        if (!horizontal) {
            for (int i = 0; i < this.comparativeBattleField.length; i++) {
                if (isFound) break;
                for (int j = 0; j < this.comparativeBattleField[i].length; j++) {
                    if (this.comparativeBattleField[i][j].equals("F")) {
                        startPoint = i;
                        row = j;
                        if (startPoint == 0) {
                            shipSize++;
                            endPoint = startPoint + shipSize;
                            isFound = true;
                            break;
                        } else {
                            startPoint -= 1;
                            shipSize += 2;
                            endPoint = startPoint + shipSize;
                            isFound = true;
                            break;
                        }
                    }
                }
            }

            if (endPoint > 9) shipSize--;
            for (int i = startPoint; i <= shipSize + startPoint - 1; i++) {
                this.comparativeBattleField[i][row] = "O";
                if (row - 1 > 0) this.comparativeBattleField[i][row - 1] = "O";
                if (row + 1 < 10) this.comparativeBattleField[i][row + 1] = "O";
            }
        }
    }

    String takeShot(String coordinates) throws RuntimeException {
        if (coordinates.length() <= 3 && coordinates.length() >= 2) {
            int tempC = 0;
            int tempR = 0;
            tempC = coordinates.charAt(0) - 65;
            tempR = coordinates.length() > 2 ? Integer.parseInt(coordinates.substring(1, 3)) - 1
                    : Integer.parseInt(coordinates.substring(1, 2)) - 1;
            if (tempC < 0 || tempC > 9 || tempR < 0 || tempR > 9) throw new RuntimeException("Error! " +
                    "You entered the wrong coordinates! Try again:");
            if (this.mainBattleField[tempC][tempR].equals("O")) {
                this.mainBattleField[tempC][tempR] = "X";
                this.fogBattleField[tempC][tempR] = "X";
                this.totalHealth--;
                return "X";
            } else if (this.mainBattleField[tempC][tempR].equals("~")) {
                this.mainBattleField[tempC][tempR] = "M";
                this.fogBattleField[tempC][tempR] = "M";
                return "M";
            }

        }
        return "M";
    }

    boolean isShipSink(String shot) {

        int c = shot.charAt(0) - 65;
        int r = shot.length() > 2 ? Integer.parseInt(shot.substring(1, 3)) - 1
                : Integer.parseInt(shot.substring(1, 2)) - 1;

        for (int i = r; i > 0; i--) {
            if (this.mainBattleField[c][i].equals("O")) return false;
            if (this.mainBattleField[c][i].equals("~")) break;

        }
        for (int i = r; i < 10; i++) {
            if (this.mainBattleField[c][i].equals("O")) return false;
            if (this.mainBattleField[c][i].equals("~")) break;
        }
        for (int i = c; i > 0; i--) {
            if (this.mainBattleField[i][r].equals("O")) return false;
            if (this.mainBattleField[i][r].equals("~")) break;
        }
        for (int i = c; i < 10; i++) {
            if (this.mainBattleField[i][r].equals("O")) return false;
            if (this.mainBattleField[i][r].equals("~")) break;
        }

        return true;

    }

    boolean isAnyAlive() {
        if (this.totalHealth <= 0) return false;
        else return true;
    }
}
