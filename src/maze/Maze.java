package maze;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * @author David Heywood
 * Date: January 10,2014
 */
public class Maze {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        char[][] maze = new char[17][17];
        int[] currentXY;
        String currentDir;
        String[] direction = {"North", "East", "South", "West"};
        currentDir = direction[0];
        readMaze(maze);
        currentXY = location(maze);
        
// This while loop first checks that you are not at the end of the maze.
// If you are not at the end of the maze then continue with the if-then-else
        while (currentXY[0] != 0 || currentXY[1] != 1) {
            if (canMoveRight(maze, currentDir, currentXY)) {
                currentDir = turnRight(currentDir);
                currentXY = moveForward(currentDir, currentXY);
            } else {
                currentDir = turnLeft(currentDir);
            }
            maze = breadCrumb(currentXY, maze);
        }
        printMaze(maze);
    }

//    Checks to see if the data.txt file is present in the home directory.
//    If data.txt file is not present then File not found is printed
    public static void readMaze(char m[][]) throws FileNotFoundException {
        Scanner in = null;
        char ch;
        boolean everythingOK = true;
        try {
            in = new Scanner(new FileReader("data.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            everythingOK = false;
        }
        if (everythingOK) {
            for (int r = 0; r < m.length; r++) {
                String line = new String(in.nextLine());
                for (int c = 0; c < m.length; c++) {
                    m[r][c] = line.charAt(c);
                }
            }
        } else {
            System.out.println("bye");
        }
    }

    // Receives the maze array and outputs the maze design
    public static void printMaze(char[][] x) {
        for (int r = 0; r < x.length; r++) {
            for (int c = 0; c < x[0].length; c++) {
                System.out.print(x[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Determines the current X(row) and Y(column) location of the player
    public static int[] location(char[][] x) {
        int currentX;
        int currentY;
        int[] currentXY = new int[2];
        for (int r = 0; r < x.length; r++) {
            for (int c = 0; c < x[0].length; c++) {
                if (x[r][c] == '.') {
                    currentX = r;
                    currentY = c;
                    currentXY[0] = r;
                    currentXY[1] = c;
                }
            }
        }
        return currentXY;
    }

    /**
     * @param x
     * @param currentDir
     * @param currentXY
     * @return
     *
     * Checks to see if the player can move right
     */
    public static boolean canMoveRight(char[][] x, String currentDir,
            int[] currentXY) {
        int hashCheck;
        boolean moveRight = true;
        switch (currentDir) {
            case "North":
                hashCheck = currentXY[1] + 1;
                if (x[currentXY[0]][hashCheck] == '#') {
                    moveRight = false;
                }   break;
            case "South":
                hashCheck = currentXY[1] - 1;
                if (x[currentXY[0]][hashCheck] == '#') {
                    moveRight = false;
                }   break;
            case "East":
                hashCheck = currentXY[0] + 1;
                if (x[hashCheck][currentXY[1]] == '#') {
                    moveRight = false;
                }   break;
            case "West":
                hashCheck = currentXY[0] - 1;
                if (x[hashCheck][currentXY[1]] == '#') {
                    moveRight = false;
                }   break;
        }
        return moveRight;
    }

    // Changes the current X(row) and Y(column) position of the player once
    // they move forward.
    public static int[] moveForward(String currentDir, int[] currentXY) {
        switch (currentDir) {
            case "North":
                currentXY[0] = currentXY[0] - 1;
                break;
            case "East":
                currentXY[1] = currentXY[1] + 1;
                break;
            case "South":
                currentXY[0] = currentXY[0] + 1;
                break;
            default:
                currentXY[1] = currentXY[1] - 1;
                break;
        }
        return currentXY;
    }

    // Changes the current direction of the player once they turn right
    public static String turnRight(String currentDir) {
        switch (currentDir) {
            case "North":
                currentDir = "East";
                break;
            case "East":
                currentDir = "South";
                break;
            case "South":
                currentDir = "West";
                break;
            default:
                currentDir = "North";
                break;
        }
        return currentDir;
    }

    // Changes the current direction of the player once they turn left
    public static String turnLeft(String currentDir) {
        switch (currentDir) {
            case "North":
                currentDir = "West";
                break;
            case "East":
                currentDir = "North";
                break;
            case "South":
                currentDir = "East";
                break;
            default:
                currentDir = "South";
                break;
        }
        return currentDir;
    }

    // Leaves a breadcrunb at your current XY location
    public static char[][] breadCrumb(int[] currentXY, char[][] x) {
        int row = currentXY[0];
        int column = currentXY[1];
        x[row][column] = '.';
        return x;
    }
}