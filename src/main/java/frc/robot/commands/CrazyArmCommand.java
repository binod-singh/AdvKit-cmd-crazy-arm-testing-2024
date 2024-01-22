// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.CrazyArm;
import frc.robot.subsystems.CrazyArmIO.CrazyArmIOInputs;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class CrazyArmCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final CrazyArm crazyArmSubSytem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CrazyArmCommand(CrazyArm crazyArmSS) {
    this.crazyArmSubSytem = crazyArmSS;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(crazyArmSubSytem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("1111 CrazyArmCommand:initialize");
    crazyArmSubSytem.moveArmToOtherSideCmd();    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println("Inside CrazyArmCommand:periodic");  
       
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("1111 CrazyArmCommand:end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("1111 CrazyArmCommand:isFinished :: " + CrazyArmIOInputs.m1_PositionRad);
    return CrazyArmIOInputs.m1_PositionRad > 140.0;
    //return false;
  }
}
