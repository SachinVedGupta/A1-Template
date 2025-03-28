package ca.mcmaster.se2aa4.mazerunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ValidatePathTest {

    private Navigate maze;
    private String path = "";

    @BeforeEach
    public void initializeMaze() {
      Logger logger = LogManager.getLogger();
      maze = new Navigate("./examples/test.maz.txt", logger, "");
    }

    @Test
    void testSimpleValidPath() {
      path = "FFFFFF";

      maze.setEnteredPath(path);
      assertTrue(maze.verify_path());
    }

    @Test
    void testShortPath() {
      path = "FF";
      
      maze.setEnteredPath(path);
      assertFalse(maze.verify_path());
    }

    @Test
    void testOutOfBoundsPath() {
      path = "FFLFFFFFFFF";
      
      maze.setEnteredPath(path);
      assertFalse(maze.verify_path());
    }

    @Test
    void testComplexValidPath() {
      path = "FLFFRFFFFRFFLF";
      
      maze.setEnteredPath(path);
      assertTrue(maze.verify_path());
    }

}
