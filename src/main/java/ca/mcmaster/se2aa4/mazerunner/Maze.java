package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
  protected String maze_file_path;
  protected int height = -1;
  protected int width = -1;
  protected int[][] maze;
  protected int startRow = -1;
  protected int endRow = -1;

  public Maze(String maze_path, Logger logger) {
    try {
      logger.trace("**** Reading the maze from file " + maze_path);
      BufferedReader reader = new BufferedReader(new FileReader(maze_path));
      width = reader.readLine().length();
      while (reader.readLine() != null) { height += 1; }

      maze_file_path = maze_path;
    }
    catch(Exception e) {
      logger.error("/!\\ An error has occured. The file was not found /!\\");
    }
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
      System.out.println("error");
    }
  }

  public void printMaze() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (maze[row][col] == 1) { System.out.print("WALL "); }
        else { System.out.print("PASS "); }
      }
      System.out.println();
    }
  }

  public setPoints() {
    for (int row = 0; row < height; row++) {
      if (maze[row][0] == 0) {

      }
    }
  }
}

