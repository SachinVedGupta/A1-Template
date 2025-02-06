package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Maze {
  protected String maze_file_path;
  protected int height = -1;
  protected int width = -1;
  protected int[][] maze;
  protected int startRow;
  protected int endRow;
  protected Logger logger;

  public Maze(String maze_path, Logger logger) {
    try {
      this.logger = logger;
      logger.trace("**** Reading the maze from file " + maze_path);
      BufferedReader reader = new BufferedReader(new FileReader(maze_path));
      width = reader.readLine().length();
      while (reader.readLine() != null) { height += 1; }

      maze_file_path = maze_path;
    }
    catch(Exception e) {
      logger.error("/!\\ An error has occured. The file was not found /!\\");
    }

    setMazeArray();
    setPoints();
  }

  public void setMazeArray() {
    maze = new int[height][width];
    try {
      BufferedReader readerIt = new BufferedReader(new FileReader(maze_file_path));
      String line;
      int row = 0;
      readerIt.readLine(); // get rid of first top row
      
      while ((line = readerIt.readLine()) != null && row < height) {
        // fill the array based on the lines contents
        for (int idx = 0; idx < line.length(); idx++) {
          if (line.charAt(idx) == '#') {
            maze[row][idx] = 1;
          }
          else if (line.charAt(idx) == ' ') {
            maze[row][idx] = 0;
          }
        }
        
        // fill any remaining space (would be the exit line)
        for (int extra = line.length(); extra < width; extra++) { maze[row][extra] = 0; }
        row++;
      }
    }
    catch(Exception e) {
      logger.error("Error in creating the maze");
    }
  }

  public void printMaze(int rowIdx, int colIdx) {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (maze[row][col] == 1) { System.out.print("#"); }
        else if (row == rowIdx && col == colIdx) { System.out.print("@"); }
        else { System.out.print(" "); }
      }
      System.out.println();
    }
  }

  public void setPoints() {
    for (int row = 0; row < height; row++) {
      if (maze[row][0] == 0) {
        startRow = row;
      }
      if (maze[row][width - 1] == 0) {
        endRow = row;
      }
    }
  }

  abstract int[] verifyOneStep(int rowIndex, int colIndex, char curr_direction, char step);
  abstract boolean printAlgorithm();
}

