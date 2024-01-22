// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CrazyArmCommand;
import frc.robot.commands.CrazyArmCommandStartPos;
import frc.robot.subsystems.CrazyArm;
import frc.robot.subsystems.CrazyArmIOSparkMax;
import frc.robot.subsystems.CrazyArmIO.CrazyArmIOInputs;
import frc.robot.subsystems.CrazyArmIO;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final CrazyArm crazyArm;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:

        crazyArm = new CrazyArm(new CrazyArmIOSparkMax()); // Spark Max
        break;

      default:
        // Replayed robot, disable IO implementations
        crazyArm = new CrazyArm(new CrazyArmIO() {});
        break;
    } 


    // Configure the button bindings
    configureButtonBindings();
  }


  private void configureButtonBindings() {

    // Schedule `armSetToYButton` when the Xbox controller's Y button is pressed,
    m_driverController.y().onTrue(new CrazyArmCommand(crazyArm).andThen(new CrazyArmCommandStartPos(crazyArm)));

    // Schedule `setArmToStartPosition` when the Xbox controller's B button is pressed,
    // m_driverController.b().onTrue(new CrazyArmCommandStartPos(crazyArm));
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous  

    return Commands.print("No autonomous command configured");
  }
}
