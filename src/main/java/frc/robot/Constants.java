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
        public final static int kRightFront = 8;
        public final static int kRightBack = 5;
        public final static int kLeftFront = 14;
        public final static int kLeftBack = 11;

        //motor directions
        public final static boolean kLeftFrontReverse = true;
        public final static boolean kLeftBackReverse = true;
        public final static boolean kRightFrontReverse = false;
        public final static boolean kRightBackReverse = false;

        //amount of slack the robot's yaw angle can be off by during alignment
        public final static double kAlignmentSlack = 2;
        public final static double kTurnSlack = 2;

        //amount of slack allowed for the limelight travel distance command in inches
        public final static double kLimelightTravelSlack = 1;

        //PID constants for turn drive
        public final static double kTurnDriveP = 0.015;
        public final static double kTurnDriveI = 0;
        public final static double kTurnDriveD = 0.000;

        //PID constants for encoder drive
        public final static double kEncoderDriveP = 0.0005;
        public final static double kEncoderDriveI = 0;
        public final static double kEncoderDriveD = 0.0002;

        //PID constants for limelight travel distance
        public final static double kLimelightTravelP = 0.02;
        public final static double kLimelightTravelI = 0;
        public final static double kLimelightTravelD = 0;

        //PID constant used to manipulate the drive speed when aligning
        public final static double kAlignmentP = 0.005; 
        public final static double kAlignmentI = 0;
        public final static double kAlignmentD = 0;    
    }

    public final class LimelightConstants {
        //limelight's mount angle on the robot (currently for kevin)
        public final static double kMountAngle = 0; 
        //limelight's height from ground (currently for kevin)
        public final static double kMountHeight = 12; 

        //minimum delays needed to let our pipeline switch (in 20 milliseconds)
        public final static double kSwitchDelay = 5;
        public final static double kMinimumTime = 10;
    }

    public final class LEDConstants {
        //number of leds on the strip
        public final static int kLEDCount = 10;

        //PWM port the led data cables are connected to
        public final static int kLED = 0;
    }

    public final class ArmConstants {
        //pivot motor ports
        public final static int kleftPivot = 17; 
        public final static int kRightPivot = 2; 
        
        //direction of the pivot motors
        public final static boolean kLeftPivotReverse = true;
        public final static boolean kRightPivotReverse = false; 

        //pneumatics channels for the bicep piston
        public final static int kArmForwardChannel = 2; 
        public final static int kArmReverseChannel = 3; 

        //how far the arm can reach when in scoring position (placeholder value)
        public final static double kArmReach = 35; 

        //angles for auto pivot
        public final static double kStandardAngle = 0;
        public final static double kRetractAngle = 20;
        public final static double kCubeAngle = 80;
        public final static double kConeAngle = 95;

        //amount of slack the pivot angle can be off by during pivoting
        public final static double kAutoPivotSlack = 3;

        //Dpad adjustment
        public final static double kAdjustmentAngle = 2;
        public final static double kAdjustmentSlackCounts = 5;

        //max and min pivoting angle in counts
        public final static double kMaxPivot = 2500;
        public final static double kMinimumPivot = 0;

        //stalling speed and position
        public final static double kStallSpeed = 0.02;
        public final static double kStallCounts = 1300;

        //PID constants for the pivot
        public final static double kPivotP = 0.2;
        public final static double kPivotI = 0;
        public final static double kPivotD = 0;
    }
    
    public final class ClawConstants {
        //pneumatics channels for the claw pistons
        public final static int kClawForwardChannel = 1; 
        public final static int kClawReverseChannel = 0;  
    }

    public final class EngagementConstants {
        //minimum change in angle needed for the navx to change p-values
        public final static double kAngleChange = 2;

        //amount of time the robot needs to be considered balanced to end the command (in 20 milliseconds)
        public final static double kBalanceTimer = 50;

        //PID constants for the engagement command
        public final static double kStationFastP = 0.01; 
        public final static double kStationSlowP = 0.0055;
        public final static double kStationI = 0; 
        public final static double kStationD = -0.0003;

        //amount of slack the robot's pitch angle is allowed during engaging
        public final static double kChargingStationSlack = 4;
    }

    public static final class RobotConstants {
        //hardware specs used for auto
        public final static double kWheelDiameter = 5.875;
        public final static int kCountsPerRev = 42;
        
        public final static double kGearRatio = 12.5; 
        public final static double kArmGearRatio = 180; //Shoulder Pivot's Gear Ratio for one full rotation
       
        public final static double kWheelCircumference = Math.PI * kWheelDiameter;

        //navx angle corrections
        public final static double kPitchOffset = 2; //offset used to set the navx pitch angle to 0
    }

    public static final class GameConstants {
        //vision constants
        public final static double kAprilTagHeight = 18.25;
        public final static double kReflectiveTapeHeight = 22.125; 
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

        //joystick deadzones
        public final static double kDeadzone = 0.1;
    }
}
