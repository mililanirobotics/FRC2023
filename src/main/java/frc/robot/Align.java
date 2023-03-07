// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

// private NetworkTable table;

/** Add your docs here. */
public class Align {
  Drive drive = new Drive();
  Limelight aprilTags = new Limelight();


  // ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  AHRS gyrox;
  boolean alignDone = false;

  /**
   * Moves the robot within a certain range from the apriltag or relfective tape based on estimate distance calculated from limelight angle offset
   */
  // public void distanceAlign() {
  //     double targetDistance = aprilTags.estimateDepthToTarget() - 50; // subtracting limelight's distance by claw's reach, subject to change
  //     double speed = 0;
  //     double kPAlignDistance = 0.01;

  //     if (targetDistance < -2 || targetDistance > 2) {
  //         speed = targetDistance * kPAlignDistance;
  //         if (Math.abs(speed) > 0.15) {
  //             speed = Math.copySign(0.15, speed);
  //         }
  //         else if (Math.abs(speed) < 0.10) {
  //             speed = Math.copySign(0.10, speed);
  //         }
  //         Drive.leftFront.set(speed);
  //         Drive.rightFront.set(speed);
  //         Drive.leftBack.set(speed);
  //         Drive.rightBack.set(speed);
  //     }
  //     else {
  //         Drive.leftFront.stopMotor();
  //         Drive.rightFront.stopMotor();
  //         Drive.leftBack.stopMotor();
  //         Drive.rightBack.stopMotor();
  //     }
  // }

  // /**
  //  * Turn the robot so the limelight's angle offset to the apriltag or relfective tape is as close to 0 as possible
  //  */
  // public void angleAlign() {
  //     double speed = 0;
  //     double kPAlignAngle = 0.03;
  //     double angleOffset = aprilTags.getHorizontalDegToTarget();
  //     alignDone = false;

  //     if (angleOffset < -0.5 || angleOffset > 0.5) {
  //       speed = angleOffset * kPAlignAngle;
  //       if (Math.abs(speed) > 0.1) {
  //         speed = Math.copySign(0.1, speed);
  //       }
  //       else if (Math.abs(speed) < 0.075) {
  //         speed = Math.copySign(0.075, speed);
  //       }
  //       Drive.leftFront.set(speed);
  //       Drive.rightFront.set(-speed);
  //       Drive.leftBack.set(speed);
  //       Drive.rightBack.set(-speed);
  //     }
  //     else {
  //       Drive.leftFront.stopMotor();
  //       Drive.rightFront.stopMotor();
  //       Drive.leftBack.stopMotor();
  //       Drive.rightBack.stopMotor();

  //       alignDone = true;
  //     }
  //   }
  
  public void navx () {
    gyrox = new AHRS(SPI.Port.kMXP);
  }

  public void desiredAngle (double turnDegrees) {
    double desiredAngle = gyrox.getAngle() + turnDegrees;
  }

  public void log() {
    SmartDashboard.putNumber("Current gyro position", gyrox.getAngle());
    

  //     if (angleOffset < -0.5 || angleOffset > 0.5) {
  //       speed = angleOffset * kPAlignAngle;
  //       if (Math.abs(speed) > 0.1) {
  //         speed = Math.copySign(0.1, speed);
  //       }
  //       else if (Math.abs(speed) < 0.075) {
  //         speed = Math.copySign(0.075, speed);
    //     }
    //     drive.leftFront.set(speed);
    //     drive.rightFront.set(-speed);
    //     drive.leftBack.set(speed);
    //     drive.rightBack.set(-speed);
    //   }
    //   else {
    //     drive.leftFront.stopMotor();
    //     drive.rightFront.stopMotor();
    //     drive.leftBack.stopMotor();
    //     drive.rightBack.stopMotor();

    //     alignDone = true;
    //   }
    }
}
