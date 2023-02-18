// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/** Add your docs here. */
public class Align {
    Apriltags aprilTags = new Apriltags();
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    boolean alignDone = false;

    /**
     * Moves the robot within a certain range from the apriltag or relfective tape based on estimate distance calculated from limelight angle offset
     */
    public void distanceAlign() {
        double targetDistance = aprilTags.estimateDepthToTarget() - 50; // subtracting limelight's distance by claw's reach, subject to change
        double speed = 0;
        double kPAlignDistance = 0.01;
        if (targetDistance < -2 || targetDistance > 2) {
            speed = targetDistance * kPAlignDistance;
            if (Math.abs(speed) > 0.15) {
                speed = Math.copySign(0.15, speed);
            }
            else if (Math.abs(speed) < 0.10) {
                speed = Math.copySign(0.10, speed);
            }
            Robot.drive.leftFront.set(speed);
            Robot.drive.rightFront.set(speed);
            Robot.drive.leftBack.set(speed);
            Robot.drive.rightBack.set(speed);
        }
        else {
            Robot.drive.leftFront.stopMotor();
            Robot.drive.rightFront.stopMotor();
            Robot.drive.leftBack.stopMotor();
            Robot.drive.rightBack.stopMotor();
        }
    }

    /**
     * Turn the robot so the limelight's angle offset to the apriltag or relfective tape is as close to 0 as possible
     */
    public void angleAlign() {
        double speed = 0;
        double kPAlignAngle = 0.03;
        double angleOffset = aprilTags.getHorizontalDegToTarget();
        alignDone = false;
  
        if (angleOffset < -0.5 || angleOffset > 0.5) {
          speed = angleOffset * kPAlignAngle;
          if (Math.abs(speed) > 0.1) {
            speed = Math.copySign(0.1, speed);
          }
          else if (Math.abs(speed) < 0.075) {
            speed = Math.copySign(0.075, speed);
          }
          Robot.drive.leftFront.set(speed);
          Robot.drive.rightFront.set(-speed);
          Robot.drive.leftBack.set(speed);
          Robot.drive.rightBack.set(-speed);
        }
        else {
            Robot.drive.leftFront.stopMotor();
            Robot.drive.rightFront.stopMotor();
            Robot.drive.leftBack.stopMotor();
            Robot.drive.rightBack.stopMotor();
          alignDone = true;
        }
      }
}
