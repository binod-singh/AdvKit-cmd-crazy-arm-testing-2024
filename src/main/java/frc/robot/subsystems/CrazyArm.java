// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class CrazyArm extends SubsystemBase {

  private final CrazyArmIO io;
  private final CrazyArmIOInputsAutoLogged inputs = new CrazyArmIOInputsAutoLogged();


  /** Creates a new ExampleSubsystem. */
  public CrazyArm(CrazyArmIO io) {
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Crazy Arm", inputs);
  }

  public void moveArmToOtherSideCmd(){
    io.moveArmToOtherSide();
  }

  public void moveArmToStartPosCmd(){
    io.moveArmToStartPosition();
  }


  // /** Returns a command that moves the arm */
  // public Command moveArmCommand() {
  //   return this.startEnd(
  //       () -> {
  //         io.moveArmToOtherSide();
  //       },
  //       () -> {
  //         io.setArmToStartPosition();
  //       });
  // }
  
  // /** Returns a command that moves the arm */
  // public Command moveArmBackCommand() {
  //   return this.startEnd(
  //       () -> {
  //         io.moveArmBack();
  //       },
  //       () -> {
  //         io.setArmToStartPosition();
  //       });
  // } 
  
}
