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

    if (rowIndex >= height || colIndex >= width || rowIndex < 0 || colIndex < 0) { return output; } // {-1, -1, -1} not currently inside the board --> if output[2] == -1 that its invalid
    if (maze[rowIndex][colIndex] == 1) { return output; } // {-1, -1, -1} not currently on a valid square (its a wall)

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

    // output:
        // new row index
        // new col index
        // new direction facing:
          // N = 1, E = 2, S = 3, W = 4

    // System.out.printf(" %c ", step);
    return output;
  }

  char numToDir(int numDir){
    switch (numDir) {
      case 1:
        return('N');
      case 3:
        return('S');
      case 4:
        return('W');
      case 2:
        return ('E');
    }

    return 'Z';
  }

  boolean isRight(int rowIndex, int colIndex, char curr_direction) {
    int[] one = verifyOneStep(rowIndex, colIndex, curr_direction, 'R');
    curr_direction = numToDir(one[2]);

    int[] two = verifyOneStep(rowIndex, colIndex, curr_direction, 'F');

    if (two[2] == -1) {
      return true;
    }
    return false;
  }

  boolean canFor(int rowIndex, int colIndex, char curr_direction) {
    int[] one = verifyOneStep(rowIndex, colIndex, curr_direction, 'F');

    if (one[2] == -1) {
      return false;
    }
    return true;
  }


  boolean printAlgorithm() {
    // MVP Algorithm try going forward
    System.out.println("\n\nTry Path: \n\n");

    int rowIdx = startRow;
    int colIdx = 0;
    char direction = 'E';
    
    while (colIdx != width - 1) {
      int[] ans;
      // System.out.println(isRight(rowIdx, colIdx, direction));
      if (isRight(rowIdx, colIdx, direction) && canFor(rowIdx, colIdx, direction)) {
        // move forward
        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        System.out.printf(" %c ", 'F');
      }
      else if (isRight(rowIdx, colIdx, direction)) {
        // turn left
        ans = verifyOneStep(rowIdx, colIdx, direction, 'L');
        System.out.printf(" %c ", 'L');
      }
      else { // in the open, so turn right and move forward
        // right
        // forward
        int[] ans1 = verifyOneStep(rowIdx, colIdx, direction, 'R');
        direction = numToDir(ans1[2]);
        System.out.printf(" %c ", 'R');

        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        System.out.printf(" %c ", 'R');
      }

      rowIdx = ans[0];
      colIdx = ans[1];
      direction = numToDir(ans[2]);
    }


    System.out.println("\n\nPath is done\n\n");
    return true;

    //row = startRow

    /*
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
    */
  }
}
