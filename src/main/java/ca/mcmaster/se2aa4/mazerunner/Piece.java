package ca.mcmaster.se2aa4.mazerunner;

public class Piece extends Subject {
  private int rowIndex;
  private int colIndex;
  private int curr_direction;

  private int width;
  private int height;
  private int[][] maze;

  public Piece(int rowIndex, int colIndex, int curr_direction, int width, int height, int[][] maze) {
      this.rowIndex = rowIndex;
      this.colIndex = colIndex;
      this.curr_direction = curr_direction;
      this.width = width;
      this.height = height;
      this.maze = maze;
  }


  public int getRowIndex() {
      return this.rowIndex;
  }

  public int getColIndex() {
      return this.colIndex;
  }

  public int getCurrDirection() {
      return this.curr_direction;
  }
  
  public void setState(int[] location) {
      this.rowIndex = location[0];
      this.colIndex = location[1];
      this.curr_direction = location[2];
      this.notifyAllObservers();
  }

  // verifies a step/move and adjusts the state accordingly with the new position/direction of the piece
  public void doMove(char step) {

    int[] output = {-1, -1, -1};

    // updates the direction, rowIndex, colIndex based on the inputted step/action
    if (step == 'F') { 
      switch (curr_direction) {
        case 1:
          rowIndex -= 1;
          break;
        case 3:
          rowIndex += 1;
          break;
        case 4:
          colIndex -= 1;
          break;
        case 2:
          colIndex += 1;
          break;
      }
    }
    else if (step == 'L') { 
      switch (curr_direction) {
        case 1:
          curr_direction = 4;
          break;
        case 3:
          curr_direction = 2;
          break;
        case 4:
          curr_direction = 3;
          break;
        case 2:
          curr_direction = 1;
          break;
      }
    }
    else if (step == 'R') { 
      switch (curr_direction) {
        case 1:
          curr_direction = 2;
          break;
        case 3:
          curr_direction = 4;
          break;
        case 4:
          curr_direction = 1;
          break;
        case 2:
          curr_direction = 3;
          break;
      }
    }
    else { setState(output); return; }

    // verifies if the new points are valid
    if (rowIndex >= height || colIndex >= width || rowIndex < 0 || colIndex < 0) { setState(output); return; } // {-1, -1, -1} not currently inside the board --> if output[2] == -1 that its invalid
    if (maze[rowIndex][colIndex] == 1) { setState(output); return; } // {-1, -1, -1} not currently on a valid square (its a wall)

    // format the output array with the new row index, col index, and direction
    output[0] = rowIndex;
    output[1] = colIndex;

    int direction = -1;
    switch (curr_direction) {
      case 1:
        direction = 1;
        break;
      case 3:
        direction = 3;
        break;
      case 4:
        direction = 4;
        break;
      case 2:
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
    setState(output); 
  }
}