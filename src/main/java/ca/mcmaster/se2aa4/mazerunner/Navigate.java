package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

class Navigate extends Maze {
  private String entered_path;

  // Navigate constructor for creating an object of Maze and for setting the instance variable
  public Navigate(String maze_path, Logger logger, String entered_path) {
    super(maze_path, logger);
    this.entered_path = entered_path;
  }

  // verifies a step and outputs the new location and direction
  int[] verifyOneStep(int rowIndex, int colIndex, char curr_direction, char step) {
    int[] output = {-1, -1, -1};

    // updates the direction, rowIndex, colIndex based on the inputted step/action
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
    else { return output; }

    // verifies if the new points are valid
    if (rowIndex >= height || colIndex >= width || rowIndex < 0 || colIndex < 0) { return output; } // {-1, -1, -1} not currently inside the board --> if output[2] == -1 that its invalid
    if (maze[rowIndex][colIndex] == 1) { return output; } // {-1, -1, -1} not currently on a valid square (its a wall)

    // format the output array with the new row index, col index, and direction
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

  char numToDir(int numDir){ // converts a numeric direction to the allocating character representation
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

  boolean isRight(int rowIndex, int colIndex, char curr_direction) { // checks if there is a wall on the right
    int[] one = verifyOneStep(rowIndex, colIndex, curr_direction, 'R');
    curr_direction = numToDir(one[2]);

    int[] two = verifyOneStep(rowIndex, colIndex, curr_direction, 'F');

    if (two[2] == -1) {
      return true;
    }
    return false;
  }

  boolean canFor(int rowIndex, int colIndex, char curr_direction) { // checks if it can go forward
    int[] one = verifyOneStep(rowIndex, colIndex, curr_direction, 'F');

    if (one[2] == -1) {
      return false;
    }
    return true;
  }

  String canonicalToFactorized(StringBuffer canonical) { // turns a canonical path into a factorized format
    StringBuffer factorized = new StringBuffer();

    int count = 0;
    char first = canonical.charAt(0);

    for (int i = 0; i < canonical.length(); i++) { // loop through each character in the inputted path
      if (canonical.charAt(i) != first) {
        // if a new character comes, print the previous factor and reset the count/first variables
        if (count > 1) { factorized.append(String.format("%d%c ", count, first)); }
        else { factorized.append(String.format("%c ", first)); }

        count = 1;
        first = canonical.charAt(i);
      }
      else {
        count += 1;
      }
    }

    // append the final factor which wasn't previously accounted for
    if (count > 1) { factorized.append(String.format("%d%c ", count, first)); }
    else { factorized.append(String.format("%c ", first)); }

    return factorized.toString();
  }

  void printAlgorithm() { // calculate a valid path and print it out to the user
    StringBuffer path = new StringBuffer();

    int rowIdx = startRow;
    int colIdx = 0;
    char direction = 'E';

    System.out.println("Maze: \n");
    printMaze(rowIdx, colIdx);
    System.out.println("\n");
    

    while (colIdx != width - 1) { // while not at the exit point

      int[] ans;
      if (isRight(rowIdx, colIdx, direction) && canFor(rowIdx, colIdx, direction)) { // move forward to follow the wall
        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        path.append("F");
      }
      else if (isRight(rowIdx, colIdx, direction)) { // is blocked in right, so turn right
        ans = verifyOneStep(rowIdx, colIdx, direction, 'L');
        path.append("L");
      }
      else { // in the open so turn right and then move forward
        int[] ans1 = verifyOneStep(rowIdx, colIdx, direction, 'R');
        direction = numToDir(ans1[2]);
        path.append("R");

        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        path.append("F");
      }

      rowIdx = ans[0];
      colIdx = ans[1];
      direction = numToDir(ans[2]);

      // Print the maze and location currently at
      /*
      System.out.println("\n\n\n");
      printMaze(rowIdx, colIdx);
      System.out.println("\n"); */
      
    }

    System.out.printf("\n\nNormal: %s\n\n", path);
    System.out.printf("Factorized: %s\n\n", canonicalToFactorized(path));
  }

  boolean verify_path() {
    System.out.println("Maze: \n");
    printMaze(startRow, 0);
    System.out.println("\n");

    if (entered_path == "") { System.out.println("Entered Path does NOT work\n\n"); return false; }
    int rowIdx = startRow;
    int colIdx = 0;
    char dir = 'E';

    // execute the inputted actions
    for (int i = 0; i < entered_path.length(); i++) {
      int[] ans = verifyOneStep(rowIdx, colIdx, dir, entered_path.charAt(i));
      if (ans[2] == -1) { System.out.println("Entered Path does NOT work\n\n"); return false; } // fails if an action wasn't valid

      rowIdx = ans[0];
      colIdx = ans[1];
      dir = numToDir(ans[2]);

      if (colIdx == width - 1) { System.out.printf("Entered Path of %s does WORK!\n\n", this.entered_path); return true; } // success if reached exit location
      
    }

    System.out.println("You did not finish at the EXIT\n\n"); // fails if didn't make it to the exit
    return true;
  }
}
