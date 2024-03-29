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

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.InverseKinematics;

/**
 * This drive implementation is for Spark Maxes driving brushed motors (e.g. CIMS) with no encoders.
 * For the Spark Flex in docked mode, replace all instances of "CANSparkMax" with "CANSparkFlex".
 */
public class CrazyArmIOSparkMax implements CrazyArmIO {
  private CANSparkMax m_motor;
  private CANSparkMax m_motor2;
  private RelativeEncoder m_encoder;
  private RelativeEncoder m_encoder2;
  private SparkPIDController m_pidController, m_pidController2;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  public double theta1x1, theta2x2;

  public CrazyArmIOSparkMax() {
    m_motor = new CANSparkMax(10, CANSparkMax.MotorType.kBrushless);
    m_motor2 = new CANSparkMax(11, CANSparkMax.MotorType.kBrushless);

    kP = 0.04;
    kMaxOutput = 0.1;
    kI = 0.0;
    kD = 0.01;

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);


    //m_encoder = new Encoder(kEncoderPortA, kEncoderPortB);
    m_encoder = m_motor.getEncoder();
    m_encoder2 = m_motor2.getEncoder();

    m_motor.setSmartCurrentLimit(8);
    m_motor2.setSmartCurrentLimit(8);


    m_pidController = m_motor.getPIDController();
    m_pidController2 = m_motor2.getPIDController();


    m_encoder.setPosition(0);
    m_encoder2.setPosition(0);

    // Use SetDistancePerPulse to set the multiplier for GetDistance
    // This is set up assuming a 6 inch wheel with a 360 CPR encoder.
    // m_encoder.setDistancePerPulse((Math.PI * 6) / 360.0);
    m_pidController.setP(kP);
    m_pidController.setOutputRange(-kMaxOutput, kMaxOutput);
    m_pidController.setI(kI);
    m_pidController.setD(kD);

    m_pidController2.setP(kP);
    m_pidController2.setOutputRange(-kMaxOutput, kMaxOutput);
    m_pidController2.setI(kI);
    m_pidController2.setD(kD);
  }

  @Override
  public void updateInputs(CrazyArmIOInputs inputs) {
    inputs.m1_PositionRad = Units.rotationsToRadians(m_encoder.getPosition());
    inputs.m1_VelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(m_encoder.getVelocity());
    inputs.m1_AppliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
    inputs.m1_CurrentAmps = new double[] {m_motor.getOutputCurrent()};

    inputs.m2_PositionRad = Units.rotationsToRadians(m_encoder2.getPosition());
    inputs.m2_VelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(m_encoder2.getVelocity());
    inputs.m2_AppliedVolts = m_motor2.getAppliedOutput() * m_motor2.getBusVoltage();
    inputs.m2_CurrentAmps = new double[] {m_motor2.getOutputCurrent()};
  }

  
  public void moveArmToStartPosition() {
    System.out.println("Inside setArmToStartPosition");

    m_encoder.setPosition(0);
    m_motor.getPIDController().setReference(0, CANSparkMax.ControlType.kPosition);
    m_encoder2.setPosition(0);
    m_pidController2.setReference(0, CANSparkMax.ControlType.kPosition);
  }

  public void moveArmToOtherSide() {
    System.out.println("Inside moveArmToOtherSide");

    double[] values = getArmThetaValues(70.8, 0);

    double theta1x1 = values[0];
    double theta2x2 = values[1];

    System.out.println("Theta 1 pos:" + theta1x1);
    System.out.println("Theta 2 pos:" + theta2x2);

    if (Double.isNaN(theta1x1) || Double.isNaN(theta2x2)) return;

    m_pidController.setReference(theta1x1, CANSparkMax.ControlType.kPosition);
    m_pidController2.setReference(-theta2x2, CANSparkMax.ControlType.kPosition);

    System.out.println(theta1x1);
    System.out.println(theta2x2);
  }

  public void moveArmToTestPosition(){
    System.out.println("11- Inside moveArmToTestPosition");

    final double armLen = 20;
    double angle = 0;
    double[] points = {10.0, 30.0};
    for (int j = 0; j < 5; j++) {
      System.out.println(points[0] + " " + points[1]);

      double[] angles = getArmThetaValues(points[0], points[1]);

      System.out.println("Angles: " + angles[0] + " " + angles[1]);

      m_pidController.setReference(angles[0], CANSparkMax.ControlType.kPosition);
      m_pidController2.setReference(angles[1], CANSparkMax.ControlType.kPosition);
    
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      
      points[0] = points[0] + Math.cos(angle) * armLen;
      points[1] = points[1] + Math.sin(angle) * armLen;
      angle += (180+36) * Math.PI / 180.0;
    }

    System.out.println("22- Inside moveArmToTestPosition");
  }  

  public double[] getArmThetaValues(double x, double y) {
      double[] out = InverseKinematics.calculateTheta(38.1, 32.8, x, y);
  
      double theta1 = Math.toDegrees(out[0]);
      double theta2 = Math.toDegrees(out[1]);
      double theta1x = theta1 * (2688/360);
      double theta2x = theta2 * (2688/360);
      double theta1x1 = theta1x;
      double theta2x2 = theta2x;
  
      double[] values = {theta1x1, theta2x2};
      return values;
    }

}
