package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.implementation_logic.Navigate;

public class GeneratePathCommand implements Command {
  private Navigate navigate;

  public GeneratePathCommand(Navigate navigate) {
      this.navigate = navigate;
  }

  @Override
  public void execute() {
      navigate.printAlgorithm();
  }
}
