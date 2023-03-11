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

        //motor directions
        public final static boolean kLeftFrontReverse = false;
        public final static boolean kLeftBackReverse = false;
        public final static boolean kRightFrontReverse = true;
        public final static boolean kRightBackReverse = true;
    }

    public final class LimelightConstants {
        //limelight's mount angle on the robot (currently for kevin)
        public final static double kMountAngle = 62; 
        //limelight's height from ground (currently for kevin)
        public final static double kMountHeight = 29.5; 
        //how far the arm can reach when in scoring position (placeholder value)
        public final static double kArmReach = 50; 

        //temporary notes for setting dual crosshair on limelight
        public final static double MinDistance = 16; 
        public final static double MaxDistance = 75.5;
    }

    public final class PivotConstants {
        //pivot motor ports
        public final static int kleftPivot = 1; 
        public final static int kRightPivot = 2; 
        
        //direction of the pivot motors
        public final static boolean kLeftPivotReverse = false;
        public final static boolean kRightPivotReverse = true; 

        //pneumatics channels for the bicep piston
        public final static int kArmForwardChannel = 0; //tbd (the piston that controls the arm)
        public final static int kArmReverseChannel = 1; //tbd
    }
    
    public final class ClawConstants {
        //pneumatics channels for the claw pistons
        public final static int kLeftClawForwardChannel = 0; //tbd
        public final static int kLeftClawReverseChannel = 1; //tbd
        public final static int kRightClawForwardChannel = 4; //tbd
        public final static int kRightClawReverseChannel = 5; //tbd
    }

    public static final class RobotConstants {
        //hardware specs used for auto
        public final static int kWheelDiameter = 6;
        public final static int kCountsPerRev = 42;
        public final static double kDriveFreeSpeed = 5676; //Empirical free speed of drive motors (RPM)
        
        public final static double kGearRatio = 7.005; //fitted for kevin
        public final static double kWristGearRatio = 2; //Wrist Pivot's gear Ratio
        public final static double kElbowGarRatio = 45; //Elbow Pivot's gear Ratio
        public final static double kArmGearRatio = 180; //Shoulder Pivot's Gear Ratio for one full rotation
       
        public final static double kWheelCircumference = Math.PI * kWheelDiameter;

        //PID constants for the engagement command
        public final static double kStationP = 0.02; 
        public final static double kStationI = 0; 
        public final static double kStationD = -0.0003; 

        //PID constants for the pivot
        public final static double kPivotP = 0;
        public final static double kPivotI = 0;
        public final static double kPivotD = 0;

        //PID constants for turn drive
        public final static double kTurnDriveP = 0.015;
        public final static double kTurnDriveI = 0.0015;
        public final static double kTurnDriveD = 0.0005;
        
        //PID constants for encoder drive (Previous P: 0.0025, I: 0.00001, D: 0.00005)
        public final static double kEncoderDriveP = 0.0025;
        public final static double kEncoderDriveI = 0.00001;
        public final static double kEncoderDriveD = 0.00015;

        //Proportoinal constant used to manipulate the drive speed when aligning
        public final static double kTurnP = 0.005; //tbd
        public final static double kTurnI = 0;
        public final static double kTurnD = 0;

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
        public final static double kChargingStationSlack = 4;
        
        //amount of slack the robot's yaw angle can be off by during alignment
        public final static double kAlignmentSlack = 2;
        public final static double kTurnSlack = 2;

        //vision constants
        public final static double kAprilTagHeight = 60;
        public final static double kReflectiveTapeHeight = 0; //tbd
        public final static int kPoleSpace = 17; //space between the med and high poles in inches
    }

    public static final class JoystickConstants {
        //joystick port for the gamepad
        public final static int kPrimaryPort = 0;
        public final static int kSecondaryPort = 1;

        //button ports
        public final static int kAButtonPort = 0;
        public final static int kBButtonPort = 1;
        public final static int kXButtonPort = 2;
        public final static int kYButtonPort = 3;
        public final static int kLeftBumperPort = 4;
        public final static int kRightBumperPort = 5;
        public final static int kBackButtonPort = 6;
        public final static int kStartButtonPort = 7;

        //axis ports
        public final static int kLeftYJoystickPort = 1;
        public final static int kLeftTriggerPort = 2;
        public final static int kRightTriggerPort = 3;
        public final static int kRightYJoystickPort = 5;
    }

    public static final class AutonomousConstants {
        public static final String kDockAndEngage1 = "Score then Dock and Engage Left";
        public static final String kDockAndEngage2 = "Score then Dock and Engage Middle";
        public static final String kDockAndEngage3 = "Score then Dock and Engage Right";
        public static final String kScoring1 = "Scoring only Left";
        public static final String kScoring2 = "Scoring only Middle";
        public static final String kScoring3 = "Scoring only Right";
    }

}
