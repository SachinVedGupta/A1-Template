package ca.mcmaster.se2aa4.mazerunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.implementation_logic.Maze;
import ca.mcmaster.se2aa4.mazerunner.implementation_logic.Navigate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MazeTest {

    private Maze maze;
    private Maze maze_invalid;

    @BeforeEach
    public void initializeMaze() {
      Logger logger = LogManager.getLogger();
      maze = new Navigate("./examples/direct.maz.txt", logger, "");
      maze_invalid = new Navigate("./examples/tiny_invalid.maz.txt", logger, "");// similar to the rectangle maze file, but with no exit point so is an invalid test case
    }

    @Test
    void testSetMazeArray() {
        // based on the code, when creating a new Navigate object, the maze array will be set
        // so am creating test case to ensure that the set maze array is correct

        int[][] expected_maze_array = {
          {0, 0, 1, 1, 1, 1, 1, 1},
          {1, 0, 1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 0, 0, 1, 1},
          {1, 1, 1, 1, 0, 0, 1, 1},
          {1, 1, 1, 1, 1, 0, 0, 0}
        };

        int[][] expected_invalid_array = {
          {1, 0, 0, 0, 0, 0, 1},
          {1, 1, 1, 0, 1, 1, 1},
          {1, 0, 0, 0, 0, 0, 1},
          {1, 1, 1, 0, 1, 1, 1},
          {0, 0, 0, 0, 0, 0, 1}
        };

        assertArrayEquals(expected_maze_array, maze.getMazeArray());
        assertArrayEquals(expected_invalid_array, maze_invalid.getMazeArray());
    }
    
    @Test
    void testPrintMazeArray() {
        // assuming the maze array is set correctly (validated by the testSetMazeArray test case)
        // must ensure that the printMazeArray method prints the maze correctly

        String expectedOutput = 
          "########\r\n" +
          "@ ######\r\n" +
          "# ######\r\n" +
          "#     ##\r\n" +
          "####  ##\r\n" +
          "#####   \r\n" +
          "########\r\n";

        // Redirecting System.out to a variable
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        maze.printMaze(0, 0);

        assertEquals(expectedOutput, outContent.toString());
        System.setOut(originalOut);


        String expectedOutput2 = 
        "#######\r\n" +
        "#@    #\r\n" +
        "### ###\r\n" +
        "#     #\r\n" +
        "### ###\r\n" +
        "      #\r\n" +
        "#######\r\n";

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream originalOut2 = System.out;
        System.setOut(new PrintStream(outContent2));
        maze_invalid.printMaze(0, 1);

        assertEquals(expectedOutput2, outContent2.toString());
        System.setOut(originalOut2);

    }

    @Test
    void testSetPoints() {
        // based on the code, when creating a new Navigate object, the maze array entry/exit points will be set
        // so must validate that these are correct

        // note that in setPoints function, row starts at 0 and ignores te first (full) row
        assertEquals(0, maze.getStartRow());
        assertEquals(4, maze.getEndRow());

        assertEquals(4, maze_invalid.getStartRow());
        assertEquals(-1, maze_invalid.getEndRow()); // -1 as there is no exit point
    }

}
