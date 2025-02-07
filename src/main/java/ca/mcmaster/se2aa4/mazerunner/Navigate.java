package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.Logger;

class Navigate extends Maze {
  private String entered_path;

  public Navigate(String maze_path, Logger logger, String entered_path) {
    super(maze_path, logger);
    this.entered_path = entered_path;

    if (entered_path != "") {
      verify_path();
    }
    else {
      printAlgorithm();
    }
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
    else { return output; }

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

  String canonicalToFactorized(StringBuffer canonical) {
    StringBuffer factorized = new StringBuffer();

    int count = 0;
    char first = canonical.charAt(0);
    for (int i = 0; i < canonical.length(); i++) {
      if (canonical.charAt(i) != first) {
        if (count > 1) { factorized.append(String.format("%d%c ", count, first)); }
        else { factorized.append(String.format("%c ", first)); }

        count = 1;
        first = canonical.charAt(i);
      }
      else {
        count += 1;
      }
    }
    if (count > 1) { factorized.append(String.format("%d%c ", count, first)); }
    else { factorized.append(String.format("%c ", first)); }

    return factorized.toString();
  }


  void printAlgorithm() {
    // MVP Algorithm try going forward
    System.out.println("\n\nTry Path: \n\n");
    StringBuffer path = new StringBuffer();

    int rowIdx = startRow;
    int colIdx = 0;
    char direction = 'E';

    System.out.println("\n\n\n");
    printMaze(rowIdx, colIdx);
    System.out.println("\n");
    
    while (colIdx != width - 1) {

      int[] ans;
      // System.out.println(isRight(rowIdx, colIdx, direction));
      if (isRight(rowIdx, colIdx, direction) && canFor(rowIdx, colIdx, direction)) {
        // move forward
        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        path.append("F");
      }
      else if (isRight(rowIdx, colIdx, direction)) {
        // turn left
        ans = verifyOneStep(rowIdx, colIdx, direction, 'L');
        path.append("L");
      }
      else { // in the open, so turn right and move forward
        // right
        // forward
        int[] ans1 = verifyOneStep(rowIdx, colIdx, direction, 'R');
        direction = numToDir(ans1[2]);
        path.append("R");

        ans = verifyOneStep(rowIdx, colIdx, direction, 'F');
        path.append("F");
      }

      rowIdx = ans[0];
      colIdx = ans[1];
      direction = numToDir(ans[2]);

      
      System.out.println("\n\n\n");
      printMaze(rowIdx, colIdx);
      System.out.println("\n"); 
      
    }



    System.out.printf("\n\n%s\n\n", path);
    System.out.printf("\nFactorized: \n%s\n\n", canonicalToFactorized(path));
    System.out.println("\n\nPath is done\n\n");
  }

  boolean verify_path() {
    System.out.println(entered_path);
    if (entered_path == "") { System.out.println("\n\nEntered Path does NOT work\n\n"); return false; }
    int rowIdx = startRow;
    int colIdx = 0;
    char dir = 'E';

    for (int i = 0; i < entered_path.length(); i++) {
      int[] ans = verifyOneStep(rowIdx, colIdx, dir, entered_path.charAt(i));
      if (ans[2] == -1) { System.out.println("\n\nEntered Path does NOT work\n\n"); return false; }

      rowIdx = ans[0];
      colIdx = ans[1];
      dir = numToDir(ans[2]);

      if (colIdx == width - 1) { System.out.println("\n\nEntered Path does WORK!\n\n"); return true; }
      
    }

    System.out.println("\n\nYou did not finish at the EXIT\n\n");
    return true;
  }
}
