package ca.mcmaster.se2aa4.mazerunner.commands;

import ca.mcmaster.se2aa4.mazerunner.implementation_logic.Navigate;

public class ValidatePathCommand implements Command {
  private Navigate navigate;

  public ValidatePathCommand(Navigate navigate) {
      this.navigate = navigate;
  }

  @Override
  public void execute() {
      navigate.verify_path();
  }
}