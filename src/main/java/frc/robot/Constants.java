// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        //motor ports
        public final static int kRightFront = 10;
        public final static int kRightBack = 11;
        public final static int kLeftFront = 12;
        public final static int kLeftBack = 13;

        public final static boolean kLeftReverse = false;
        public final static boolean kRightReverse = true;

        //encoder ports & declarations
        public final static boolean kRightEncoderReverse = true;
        public final static boolean kLeftEncoderReverse = false;

        //motor directions
        public final static boolean kLeftFrontReverse = false;
        public final static boolean kLeftBackReverse = false;
        public final static boolean kRightFrontReverse = true;
        public final static boolean kRightBackReverse = true;

    }

    public final class LimelightConstants {
        public final static double kMountAngle = 39;
        public final static double kMountHeight = 24; //limelight's height from ground (currently for kevin)

        //pipeline numbers for the limelight (WIP)
        public final static int kReflectivePipeline = 0;
        public final static int kAprilTagPipeline = 0;
        public final static int kAlignmentPipelineHigh = 0; //manipulates the limelight's FOV to only see the high reflective tape
        public final static int kAlignmnetPipelineMed = 0; //manipulates the limelight's FOV to only see the med reflective tape
    }

    public final class PivotConstants {
        public final static int kPivot = 1; //tbdj
        public final static boolean kPivotReverse = false; //tbd
        public final static boolean kPivotEncoderReverse = false; //tbd

        public final static int kArmForward = 1; //tbd (hte piston that controls the arm)
        public final static int kArmReverse = 2; //tbd
    }
    
    public final class ClawConstants {
        public final static int kLeftClawForward = 1; //tbd
        public final static int kLeftClawReverse = 1; //tbd

        public final static int kRightClawForward = 1; //tbd
        public final static int kRightClawReverse = 1; //tbd
    }

    public static final class RobotConstants {
        public final static int kWheelDiameter = 4;
        public final static int kCountsPerRev = 42;
        public final static double kDriveFreeSpeed = 5676; //Empirical free speed of drive motors (RPM)
        
        public final static double kGearRatio = 7.005; //fitted for kevin
        public final static double kWheelCircumference = Math.PI * kWheelDiameter;

        //PID constants for the engagement command
        public final static double kStationP = 0.02; 
        public final static double kStationI = 0; 
        public final static double kStationD = -0.0003; 

        //PID constants for turn drive
        public final static double kTurnDriveP = 0.015;
        public final static double kTurnDriveI = 0.0015;
        public final static double kTurnDriveD = 0.0005;
        
        //PID constants for alignment
        public final static double kTurnP = 0.005; //tbd

        //PID and feedforward constants for drive
        public final static double kDriveP = 0.0025;
        public final static double kDriveI = 0.00001;
        public final static double kDriveD = 0.00015;

        public final static double kDriveS = 0.05;
        public final static double kDriveV = 12 / kDriveFreeSpeed;
        public final static double kDriveA = 0;
    }

    public static final class GameConstants {
        //amount of slack the robot's pitch angle is allowed during engaging
        public final static double kChargingStationSlackRad = 2 * Math.PI / 180.0;
        public final static double kChargingStationSlack = 4;

        public final static double kStopAngleRad = 3 * Math.PI / 180;
        public final static double kStopAngle = 3;
        
        //used when engaging the charging station; fixed distance the robot has to travel to get on
        public final static double kChargingStationDistance = 42;

        //amount of slack the robot's yaw angle can be off by during alignment
        public final static double kAlignmentSlack = 3;
        public final static double kTurnSlack = 2;

        //vision constants
        public final static int kAprilTagHeight = 60;
        public final static double kReflectiveTapeHeight = 0; //tbd
        public final static int kPoleSpace = 17; //space between the med and high poles in inches
    }

    public static final class JoystickConstants {
        public final static int kControllerPort = 0;
        public final static int kLeftYJoystickPort = 1;
        public final static int kRightYJoystickPort = 5;
    }
}
