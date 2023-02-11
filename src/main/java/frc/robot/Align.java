// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/** Add your docs here. */
public class Align {
    Apriltags aprilTags = new Apriltags();
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();

    // public void Alignment() {
    //     // Set to top pipeline
    //     apriltags.setPipeline(1);
    //     Robot.drive.angleAlign();
    //     // Set to bottom pipeline
    //     apriltags.setPipeline(2);

    //     // Convert continous angle to limited to 360
    //     double currentPosition = gyro.getAngle();
    //     if (gyro.getAngle() > 360) {
    //         int number = (int) (gyro.getAngle() / 360); 
    //         currentPosition = gyro.getAngle() - (360 * number);
    //     }
    //     if (gyro.getAngle() < 0) {
    //         int number = (int) (gyro.getAngle() / 360); 
    //         currentPosition = gyro.getAngle() + (360 * number);
    //     }

    //     // Calculations for side to side distance.
    //     double totalAngle = 90 - currentPosition;
    //     double angle2 = 180 - 90 - totalAngle;
    //     double angle3 = totalAngle - apriltags.getHorizontalDegToTarget();
    //     double angleOffset = apriltags.getHorizontalDegToTarget() * Math.PI/180;
    //     double angle2radians = angle2 * Math.PI/180;
    //     double angle3radians = angle3 * Math.PI/180;

    //     double length = 17 * Math.sin(angle2radians) / Math.sin(angleOffset);
    //     double distance = Math.cos(angle3) * length;

    //     // Actual movement
    //     Robot.drive.turnRobot.drive(5000, totalAngle, 20);
    //     Robot.drive.encoderRobot.drive(30, distance, "forward", 5000);
    //     Robot.drive.turnDrive(5000, -90, 20);

    // }

    public void align2() {
        aprilTags.setPipeline(1);
        Robot.drive.angleAlign();
        double length1 = aprilTags.estimateVerticalDistance();

        aprilTags.setPipeline(2);
        double length2 = aprilTags.estimateVerticalDistance();

        double offset = aprilTags.getHorizontalDegToTarget();
        double angle1 = Math.asin(Math.sin(offset * length2)/length1);

        double angle2 = 180 - 90 - angle1;
        double distance = length1/ Math.tan(angle2);

        Robot.drive.turnDrive(5000, 90, 0.2);
        Robot.drive.encoderDrive(0.3, distance, "forward", 5000);
        Robot.drive.turnDrive(5000, 180 - angle2, 0.2);
    }

    public void distanceAlign() {
        double targetDistance = aprilTags.estimateVerticalDistance() - 50; // subtracting limelight's distance by claw's reach, subject to change
        double speed = 0;
        double k = 0.01;
        if (targetDistance < -2 || targetDistance > 2) {
            speed = targetDistance * k;
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
}
