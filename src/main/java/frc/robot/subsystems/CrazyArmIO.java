// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLog;

public interface CrazyArmIO {
  @AutoLog
  public static class CrazyArmIOInputs {
    public static double m1_PositionRad = 0.0;
    public double m1_VelocityRadPerSec = 0.0;
    public double m1_AppliedVolts = 0.0;
    public double[] m1_CurrentAmps = new double[] {};

    public double m2_PositionRad = 0.0;
    public double m2_VelocityRadPerSec = 0.0;
    public double m2_AppliedVolts = 0.0;
    public double[] m2_CurrentAmps = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(CrazyArmIOInputs inputs) {}

  /** Set to Start Position */
  public default void moveArmToStartPosition() {}

    /** Move Arm to Other side */
  public default void moveArmToOtherSide() {}

  /** Move Arm to Other side */
  public default void moveArmToTestPosition() {}


}
