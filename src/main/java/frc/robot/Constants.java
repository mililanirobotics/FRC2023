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
        public final static double kMountAngle = 62; //Limelight's mount angle on the robot (currently for kevin)
        public final static double kMountHeight = 29.5; //limelight's height from ground (currently for kevin)
        public final static double kArmReach = 50; //How far the arm can reach when in scoring position (placeholder value)

        // Temporary notes for setting dual crosshair on limelight
        public final static double MinDistance = 16; 
        public final static double MaxDistance = 75.5;

        //pipeline numbers for the limelight (WIP)
        public final static int kReflectivePipeline = 3;
        public final static int kAprilTagPipeline = 0;
        public final static int kAlignmentPipelineHigh = 0; //manipulates the limelight's FOV to only see the high reflective tape
        public final static int kAlignmnetPipelineMed = 0; //manipulates the limelight's FOV to only see the med reflective tape

        public static final double kPAlignAngle = 0.03;
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
        
        public final static double kGearRatio = 7.005; //fitted for kevin
        public final static double kWheelCircumference = Math.PI * kWheelDiameter;

        //Proportional constant used to manipulate the drive speed when engaging
        public final static double kStationP = 20.76972; //proportion needed to run at 0.29 when robot is within 40% of error
        public final static double kStationI = 4.77464829276; //calculated based on the idea that if we have 60% error and wanted to increase speed by 0.1 in 1 sec
        public final static double kStationD = 0.001; //tbd

        //PID constants for auto drive
        public final static double kDriveP = 0;
        public final static double kDriveI = 0;
        public final static double kDriveD = 0;
        
        //Proportoinal constant used to manipulate the drive speed when turning
        public final static double kTurnP = 0.005; //tbd
    }

    public static final class GameConstants {
        //amount of slack the robot's pitch angle is allowed during engaging
        public final static double kChargingStationSlack = 2 * Math.PI / 180.0;
        public final static double kStopAngle = 3 * Math.PI / 180;
        
        //used when engaging the charging station; fixed distance the robot has to travel to get on
        public final static double kChargingStationDistance = 42;

        //amount of slack the robot's yaw angle can be off by during alignment
        public final static double kAlignmentSlack = 1;

        //vision constants
        public final static int kAprilTagHeight = 60;
        public final static double kReflectiveTapeHeight = 0; //tbd
        public final static int kPoleSpace = 17; //space between the med and high poles in inches

        //currently for kevin
        
        //distance kevin stays engaged with the charging station in inches
        public final static double kChargeStationRange = 35;
        public final static double kChargeStationIntervalStart = 17;
        public final static double kChargeStationIntervalEnd = 23;
    }

    public static final class JoystickConstants {
        public final static int kControllerPort = 0;
        public final static int kLeftYJoystickPort = 1;
        public final static int kRightYJoystickPort = 5;
    }

}
