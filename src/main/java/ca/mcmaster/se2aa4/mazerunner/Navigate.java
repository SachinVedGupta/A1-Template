package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

class Navigate extends Maze {
  public Navigate(String maze_path, Logger logger) {
    super(maze_path, logger);
    printAlgorithm();
  }

  int[] verifyOneStep(int rowIndex, int colIndex, char curr_direction, char step) { // verifies a step and outputs the new location and direction
    int[] output = {-1, -1, -1};

    if (step == 'F') { 
      switch (curr_direction) {
        case 'N':
          rowIndex -= 1;
          break;
        case 'S':
          rowIndex += 1;
          break;
        case 'W':
          colIndex -= 1;
          break;
        case 'E':
          colIndex += 1;
          break;
      }
    }
    else if (step == 'L') { 
      switch (curr_direction) {
        case 'N':
          curr_direction = 'W';
          break;
        case 'S':
          curr_direction = 'E';
          break;
        case 'W':
          curr_direction = 'S';
          break;
        case 'E':
          curr_direction = 'N';
          break;
      }
    }
    else if (step == 'R') { 
      switch (curr_direction) {
        case 'N':
          curr_direction = 'E';
          break;
        case 'S':
          curr_direction = 'W';
          break;
        case 'W':
          curr_direction = 'N';
          break;
        case 'E':
          curr_direction = 'S';
          break;
      }
    }

    if (!(rowIndex < height && colIndex < width)) { return output; } // not currently on a valid square
    output[0] = rowIndex;
    output[1] = colIndex;

    int direction = -1;
    switch (curr_direction) {
      case 'N':
        direction = 1;
        break;
      case 'S':
        direction = 3;
        break;
      case 'W':
        direction = 4;
        break;
      case 'E':
        direction = 2;
        break;
    }
    output[2] = direction;

    return output;
  }

  boolean printAlgorithm() {
    // MVP Algorithm try going forward
    System.out.println("Try Path:");

    char currentDirection = 'F';
    for (int col = 0; col < width; col++) {
      if (maze[startRow][col] == 0) {
        System.out.print("F ");
      }
      else {
        System.out.println("Path did not work");
        break;
      }
    }
    return true;
  }
}
