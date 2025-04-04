package ca.mcmaster.se2aa4.mazerunner.implementation_logic;

import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.observer_pattern.Observer;

public class Navigate extends Maze implements Observer {
  private Piece piece;
  private String entered_path;
  private String generated_path = "";
  
  private int rowIndex;
  private int colIndex = 0;
  private int curr_direction;


  // Navigate constructor for creating an object of Maze and for setting the instance variable
  public Navigate(String maze_path, Logger logger, String entered_path) {
    super(maze_path, logger);
    this.entered_path = entered_path;

    // set the subject
    this.piece = new Piece(startRow, 0, 2, width, height, maze);
    this.piece.attach(this);

    update();
  }

  public void update() {
    this.rowIndex = piece.getRowIndex();
    this.colIndex = piece.getColIndex();
    this.curr_direction = piece.getCurrDirection();
  }
  
  private boolean isRight() { // checks if there is a wall on the right
    int[] current_position = {rowIndex, colIndex, curr_direction};
    boolean is_right = false;

    piece.doMove('R');
    piece.doMove('F');

    if (curr_direction == -1) {
      is_right = true;
    }

    piece.setState(current_position); // reset the piece to the original position

    return is_right;
  }

  private boolean canFor() { // checks if it can go forward
    boolean can_forward = true;
    int[] current_position = {rowIndex, colIndex, curr_direction};
    piece.doMove('F');

    if (curr_direction == -1) {
      can_forward = false;
    }

    piece.setState(current_position);
    return can_forward;
  }

  private String canonicalToFactorized(StringBuffer canonical) { // turns a canonical path into a factorized format
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
  
  public String getGeneratedPath() {
    if (generated_path == "") { return ""; }
    return generated_path + "\n" + canonicalToFactorized(new StringBuffer(generated_path));
  }

  public void printAlgorithm() { // calculate a valid path and print it out to the user
    if (startRow == -1 || endRow == -1) { // if the start or end point is not set, then return
      logger.info("Invalid Maze");
      return;
    }

    StringBuffer path = new StringBuffer();

    System.out.println("Maze: \n");
    printMaze(rowIndex, colIndex);
    System.out.println("\n");
    
    while (colIndex != width - 1) { // while not at the exit point/location
      if (isRight() && canFor()) { // move forward to follow the wall
        piece.doMove('F');
        path.append("F");
      }
      else if (isRight()) { // is blocked in right, so turn right
        piece.doMove('L');
        path.append("L");
      }
      else { // in the open so turn right and then move forward
        piece.doMove('R');
        path.append('R');

        piece.doMove('F');;
        path.append("F");
      }
    }

    generated_path = path.toString(); // set the generated path to the instance variable

    System.out.printf("\n\nNormal: %s\n\n", path);
    System.out.printf("Factorized: %s\n\n", canonicalToFactorized(path));
  }

  public void setEnteredPath(String entered_path) {
    this.entered_path = entered_path;
  }

  public boolean verify_path() {
    System.out.println("Maze: \n");
    printMaze(startRow, 0);
    System.out.println("\n");

    if (entered_path == "") { System.out.println("Entered Path does NOT work\n\n"); return false; }

    // execute the inputted actions
    for (int i = 0; i < entered_path.length(); i++) {
      piece.doMove(entered_path.charAt(i));
      if (rowIndex == -1) { System.out.println("Entered Path does NOT work\n\n"); return false; } // fails if an action wasn't valid

      if (colIndex == width - 1) { System.out.printf("Entered Path of %s does WORK!\n\n", this.entered_path); return true; } // success if reached exit location
    }

    System.out.println("You did not finish at the EXIT\n\n"); // fails if didn't make it to the exit
    return false;
  }
}
