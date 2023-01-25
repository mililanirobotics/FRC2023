package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

    public static final class DriveConstants {
        public static final int kLeftMotor1Port = 1;
        public static final int kLeftMotor2Port = 3;
        public static final int kRightMotor1Port = 2;
        public static final int kRightMotor2Port = 4;

        public static final int kEncoderCPR = 4096;
        public static final double kWheelDiameterMeters = Units.inchesToMeters(6);
        public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

        public static final double kTurnP = 1;
        public static final double kTurnI = 0;
        public static final double kTurnD = 0;

        public static final double kMaxTurnRateDegPerS = 100;
        public static final double kMaxTurnAccelerationDegPerSSquared = 300;

        public static final double kTurnToleranceDeg = 5;
        public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
      }

    public static final class LimeLightConstants{
        public static final double kTargetHeight = 2.4384;

        public static final double kMountAngle = 35;
        public static final double kLensHeight = .737; 
      }

    public static final class OIConstants {
        public static final int kJoystickPort = 0;
        public static final int kControllerPort = 1;

        public static NetworkTableEntry kXAxisSpeedMult;
        public static NetworkTableEntry kZAxisSpeedMult;

        public static NetworkTableEntry kSlowXAxisSpeedMult;
        public static NetworkTableEntry kSlowZAxisSpeedMult;
      }

}