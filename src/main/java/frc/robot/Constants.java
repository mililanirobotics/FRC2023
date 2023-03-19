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
        public final static int kRightFront = 2;
        public final static int kRightBack = 4;
        public final static int kLeftFront = 7;
        public final static int kLeftBack = 3;

        //motor directions
        public final static boolean kLeftFrontReverse = true;
        public final static boolean kLeftBackReverse = true;
        public final static boolean kRightFrontReverse = false;
        public final static boolean kRightBackReverse = false;
    }

    public final class LimelightConstants {
        //limelight's mount angle on the robot (currently for kevin)
        public final static double kMountAngle = 62; 
        //limelight's height from ground (currently for kevin)
        public final static double kMountHeight = 29.5; 
        //how far the arm can reach when in scoring position (placeholder value)
        public final static double kArmReach = 50; 

        //temporary notes for setting dual crosshair on limelight
    }

    public final class PivotConstants {
        //pivot motor ports
        public final static int kleftPivot = 5; 
        public final static int kRightPivot = 6; 
        
        //direction of the pivot motors
        public final static boolean kLeftPivotReverse = true;
        public final static boolean kRightPivotReverse = false; 

        //pneumatics channels for the bicep piston
        public final static int kArmForwardChannel = 2; 
        public final static int kArmReverseChannel = 3; 

        //angles for auto pivot
        public final static double kStandardAngle = 0;
        public final static double kRetractAngle = 0;
        public final static double kCubeAngle = 80;
        public final static double kConeAngle = 95;

        //max and min pivoting angle in counts
        public final static double kMaxPivot = 2500;
        public final static double kMinimumPivot = 0;

        //stalling speed and position
        public final static double kStallSpeed = 0.02;
        public final static double kStallCounts = 1300;
    }
    
    public final class ClawConstants {
        //pneumatics channels for the claw pistons
        public final static int kClawForwardChannel = 1; 
        public final static int kClawReverseChannel = 0;  
    }

    public static final class RobotConstants {
        //hardware specs used for auto
        public final static double kWheelDiameter = 5.875;
        public final static int kCountsPerRev = 42;
        
        public final static double kGearRatio = 5; 
        public final static double kArmGearRatio = 180; //Shoulder Pivot's Gear Ratio for one full rotation
       
        public final static double kWheelCircumference = Math.PI * kWheelDiameter;

        //PID constants for the engagement command
        public final static double kStationP = 0.0075; 
        public final static double kStationI = 0; 
        public final static double kStationD = -0.0003;

        //PID constants for the pivot
        public final static double kPivotP = 0.2;
        public final static double kPivotI = 0;
        public final static double kPivotD = 0;

        //PID constants for turn drive
        public final static double kTurnDriveP = 0.01;
        public final static double kTurnDriveI = 0;
        public final static double kTurnDriveD = 0.0015;
        
        //PID constants for encoder drive (Previous P: 0.0025, I: 0.00001, D: 0.00005)
        public final static double kEncoderDriveP = 0.0005;
        public final static double kEncoderDriveI = 0;
        public final static double kEncoderDriveD = 0.00015;

        //Proportoinal constant used to manipulate the drive speed when aligning
        public final static double kTurnP = 0.005; //tbd
        public final static double kTurnI = 0;
        public final static double kTurnD = 0;
    }

    public static final class GameConstants {
        //amount of slack the robot's pitch angle is allowed during engaging
        public final static double kChargingStationSlack = 4;
        
        //amount of slack the robot's yaw angle can be off by during alignment
        public final static double kAlignmentSlack = 2;
        public final static double kTurnSlack = 2;

        //amount of slack the pivot angle can be off by during pivoting
        public final static double kAutoPivotSlack = 3;

        //vision constants
        public final static double kAprilTagHeight = 18.25;
        public final static double kReflectiveTapeHeight = 24; 
    }

    public static final class JoystickConstants {
        //joystick port for the gamepad
        public final static int kPrimaryLeftStickPort = 0;
        public final static int kPrimaryRightStickPort = 1;
        public final static int kSecondaryPort = 2;

        //Gamepad ports
        public final static int kAButtonPort = 1;
        public final static int kBButtonPort = 2;
        public final static int kXButtonPort = 3;
        public final static int kYButtonPort = 4;
        public final static int kLeftBumperPort = 5;
        public final static int kRightBumperPort = 6;
        public final static int kBackButtonPort = 7;
        public final static int kStartButtonPort = 8;

        //Gamepad axis ports
        public final static int kLeftYJoystickPort = 1;
        public final static int kLeftTriggerPort = 2;
        public final static int kRightTriggerPort = 3;
        public final static int kRightYJoystickPort = 5;

        //Dpad values
        public final static int kDpadUp = 0;
        public final static int kDpadDown = 180;

        //Attack3 button ports
        public final static int kAttackTriggerPort = 1;
        public final static int kAttackButtonTwo = 2;

        //Attack3
        public final static int kAttackYAxisPort = 1;
    }
}
