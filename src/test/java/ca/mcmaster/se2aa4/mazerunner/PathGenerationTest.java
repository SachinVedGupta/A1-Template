package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.implementation_logic.Navigate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class PathGenerationTest {

  private Navigate maze1;
  private Navigate maze2;
  private Navigate maze3;

  @BeforeEach
  public void initializeMazes() {
    Logger logger = LogManager.getLogger();
    maze1 = new Navigate("./examples/test.maz.txt", logger, "");
    maze2 = new Navigate("./examples/test_simple.maz.txt", logger, "");
    maze3 = new Navigate("./examples/tiny_invalid.maz.txt", logger, "");
  }

  @Test
  void testComplexPathGeneration() {
    String expectedOutput = "FRFFLFFFFLFFRF\nF R 2F L 4F L 2F R F ";
    maze1.printAlgorithm();
    assertEquals(expectedOutput, maze1.getGeneratedPath());
  }

  @Test
  void testSimplePathGeneration() {
    String expectedOutput = "FFFFFF\n6F ";
    maze2.printAlgorithm();
    assertEquals(expectedOutput, maze2.getGeneratedPath());
  }

  @Test
  void testInvalidPathGeneration() {
    String expectedOutput = "";
    maze3.printAlgorithm();
    assertEquals(expectedOutput, maze3.getGeneratedPath());
  }
}
