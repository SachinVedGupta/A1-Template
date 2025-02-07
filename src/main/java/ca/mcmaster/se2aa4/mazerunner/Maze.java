package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Maze { // includes methods and functionality related to the board
  protected String maze_file_path;
  protected int height = -1;
  protected int width = -1;
  protected int[][] maze;
  protected int startRow;
  protected int endRow;
  protected Logger logger;

  public Maze(String maze_path, Logger logger) {
    try {

      // set the instance variables of logger, width, height
      this.logger = logger;
      logger.trace("**** Reading the maze from file " + maze_path);
      BufferedReader reader = new BufferedReader(new FileReader(maze_path));
      width = reader.readLine().length();
      while (reader.readLine() != null) {
        height += 1;
      }

      maze_file_path = maze_path;
    } catch (Exception e) {
      logger.error("/!\\ An error has occured. The file was not found /!\\");
    }

    // set the maze array and the maze entry/exit points
    setMazeArray();
    setPoints();
  }

  private void setMazeArray() { // convert the inputted maze txt file into a 2D integer array that represents it
    maze = new int[height][width];
    try {
      // prepare the variables and reader
      BufferedReader readerIt = new BufferedReader(new FileReader(maze_file_path));
      String line;
      int row = 0;
      readerIt.readLine(); // get rid of first top row

      // process each line --> character and add it into the array
      while ((line = readerIt.readLine()) != null && row < height) {
        // fill the array based on the lines contents
        for (int idx = 0; idx < line.length(); idx++) {
          if (line.charAt(idx) == '#') {
            maze[row][idx] = 1;
          } else if (line.charAt(idx) == ' ') {
            maze[row][idx] = 0;
          }
        }

        // fill any remaining space (would be the exit line)
        for (int extra = line.length(); extra < width; extra++) {
          maze[row][extra] = 0;
        }
        row++;
      }
    } catch (Exception e) {
      logger.error("Error in creating the maze");
    }
  }

  protected void printMaze(int rowIdx, int colIdx) { // print out the maze and current location
    for (int row = -1; row <= height; row++) {
      for (int col = 0; col < width; col++) {
        // based on the value in the array, print the character
        if (row == -1 || row == height) {
          System.out.print("#");
        } else if (maze[row][col] == 1) {
          System.out.print("#");
        } else if (row == rowIdx && col == colIdx) {
          System.out.print("@");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println(); // seperate each row of the maze
    }
  }

  private void setPoints() { // set the entry and exit points (rows)
    for (int row = 0; row < height; row++) {
      if (maze[row][0] == 0) { // assuming entry on left side, find the open space at the fully left side
        startRow = row;
      }
      if (maze[row][width - 1] == 0) { // assuming exit on right side, find the open space at the right most side
        endRow = row;
      }
    }
  }

  abstract int[] verifyOneStep(int rowIndex, int colIndex, char curr_direction, char step);

  abstract void printAlgorithm();
}
