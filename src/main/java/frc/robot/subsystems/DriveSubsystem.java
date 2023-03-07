package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotContainer;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotConstants; 


public class DriveSubsystem extends SubsystemBase {
    //declaring drive motors and encoders
    private final CANSparkMax leftFront;
    private final CANSparkMax leftBack;
    private final CANSparkMax rightFront;
    private final CANSparkMax rightBack;

    private final MotorControllerGroup leftDrive;
    private final MotorControllerGroup rightDrive;

    private final RelativeEncoder rightBackEncoder;
    private final RelativeEncoder rightFrontEncoder;
    private final RelativeEncoder leftFrontEncoder;
    private final RelativeEncoder leftBackEncoder;


    //declaring differential drive Odometry and Kinematics objects
    private DifferentialDriveOdometry odometry;
    //private DifferentialDriveKinematics kinematics;
    private DifferentialDrive drive;
    //feedforward and general drive PID
    private SimpleMotorFeedforward feedforward;
    private PIDController drivePID;

    //Declaring the gyro
    private final AHRS navx;

    //Sendable chooser for drive speeds
    SendableChooser<Double> driveSpeeds = new SendableChooser<Double>();
    private double driveScale;

    private GenericEntry leftEncoderWidget;
    private GenericEntry rightEncoderWidget;

    //constructor
    //initializes all motors, motor controllers, drive trains, and dashboard options
    public DriveSubsystem(ShuffleboardTab motorTab, ShuffleboardTab preMatchTab) {
        //initializing motors
        leftFront = new CANSparkMax(DriveConstants.kLeftFront, MotorType.kBrushless);
        leftBack = new CANSparkMax(DriveConstants.kLeftBack, MotorType.kBrushless);
        rightFront = new CANSparkMax(DriveConstants.kRightFront, MotorType.kBrushless);
        rightBack = new CANSparkMax(DriveConstants.kRightBack, MotorType.kBrushless);

        //inverting the correct motors
        leftFront.setInverted(DriveConstants.kLeftFrontReverse);
        leftBack.setInverted(DriveConstants.kLeftBackReverse);
        rightFront.setInverted(DriveConstants.kRightReverse);
        rightBack.setInverted(DriveConstants.kRightReverse);

        //creating two motor controller groups, one for each side
        leftDrive = new MotorControllerGroup(leftFront, leftBack);
        rightDrive = new MotorControllerGroup(rightFront, rightBack);

        //initializing encoders
        leftFrontEncoder = leftFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);
        leftBackEncoder = leftBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);
        rightFrontEncoder = rightFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);
        rightBackEncoder = rightBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);

        //setting conversion factor of distance to counts
        leftFrontEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);
        leftBackEncoder.setPositionConversionFactor(RobotConstants.kGearRatio * RobotConstants.kWheelCircumference / RobotConstants.kCountsPerRev);
        rightFrontEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);
        rightBackEncoder.setPositionConversionFactor(RobotConstants.kGearRatio * RobotConstants.kWheelCircumference / RobotConstants.kCountsPerRev);

        //resetting encoders upon object creation
        resetEncoders(); 

        //creating and enabling navx
        navx = new AHRS(I2C.Port.kMXP);
        navx.enableLogging(true);

        //creating odometry and differential drive (tank drive) objects
        odometry = new DifferentialDriveOdometry(navx.getRotation2d(), getLeftEncoder(), getRightEncoder());
        drive = new DifferentialDrive(leftDrive, rightDrive);

        drivePID = new PIDController(RobotConstants.kDriveP, RobotConstants.kDriveI, RobotConstants.kDriveD);
        feedforward = new SimpleMotorFeedforward(RobotConstants.kDriveS, RobotConstants.kDriveV , RobotConstants.kDriveA);

        //shuffleboard widgets
        leftEncoderWidget = motorTab.add("Left Encoder", 0).withSize(2, 1).getEntry();
        rightEncoderWidget = motorTab.add("Right Encoder", 0).withSize(2, 1).getEntry(); 

        //setting options for the user to choose the drive speed
        driveSpeeds.setDefaultOption("100%", 1.0);
        driveSpeeds.addOption("50%", 0.5);
        driveSpeeds.addOption("35%", 0.35);
        preMatchTab.add("Max Speed", driveSpeeds);
    }

    //=========================================================================== 
    // drive methods
    //===========================================================================

    /**
     * Drives the robot using tank drive controls
     *
     * @param rightSpeed controls the speed of the right drive motors
     * @param leftSpeed controls the speed of the left drive motors
    */
    public void drive(double leftPercentPower, double rightPercentPower) {
        drive.tankDrive(leftPercentPower, rightPercentPower);
    }

    /**
     * Drives the robot using tank drive controls (maintains a constant speed no matter the voltage)
     * Uses feedforward along with a PID controller in order to maintain a constant velocity
     * @param leftVelocity
     * @param rightVelocity
     */
    public void drivePID(double leftVelocity, double rightVelocity) {
        double calculatedLeftSpeed = feedforward.calculate(leftVelocity) 
            + drivePID.calculate(leftFrontEncoder.getVelocity(), leftVelocity);

        double calculatedRightSpeed = feedforward.calculate(rightVelocity) 
            + drivePID.calculate(rightFrontEncoder.getVelocity(), rightVelocity);

        leftDrive.setVoltage(Math.pow(calculatedLeftSpeed, 2));
        rightDrive.setVoltage(Math.pow(calculatedRightSpeed, 2));
    }

    public void shutdown() {
        drive.tankDrive(0, 0);
    }

    /**
     * Returns the current speed scaler selected on shuffleboard
     * @return The current speed scaler selected
     */
    public double getDriveSpeed() {
        return driveScale;
    }

    /**
     * resets all of the drive encoder values
    */
    public void resetEncoders() {
        leftFrontEncoder.setPosition(0);
        leftBackEncoder.setPosition(0);
        rightFrontEncoder.setPosition(0);
        rightBackEncoder.setPosition(0);
    }

    /**
     * returns distance traveled in inches by the left motor
    */
    public double getLeftEncoder() {
        return leftFrontEncoder.getPosition();
    }

    /**
     * returns distance traveled in inches by the right motor
    */
    public double getRightEncoder() {
        return rightFrontEncoder.getPosition();
    }

    public double convertDistance(double inches) {
        return inches * 23.4192;
    }

    /**
     * updates odometry with the robot's most recent position on the field
    */
    public void updateOdometry() {
        odometry.update(navx.getRotation2d(), getLeftEncoder(), getRightEncoder());
    }

    //=========================================================================== 
    // gyro and accelorometer methods
    //===========================================================================

    //calibrates the gyro
    public void calibrateGyro() {
        navx.calibrate();
    }

    /**
     * Resets the current angle of the gyro to 0. 
     * Tells the driver that the gyro is connected via a print statement
    */
    public void zeroOutGyro() {
        System.out.println("Gyro Connected: "+navx.isConnected());
        navx.reset();
    }

    /**
     * Gets the current yaw angle from the navx gyro
    */
    public double getYaw() {
        return navx.getYaw();
    }

    /**
     * Gets the current pitch angle from the navx gyro
    */
    public double getPitch() {
        return navx.getPitch();
    }

    /**
     * Gets the current roll angle from the navx gyro
    */
    public double getRoll() {
        return navx.getRoll();
    }


    /**
     * Gets the current acceleration vector of the z-axis in g's
    */
    public double getZ() {
        return navx.getRawAccelZ();
    }

    /**
     * Gets the current acceleration vector of the y-axis in g's
    */    public double getY() {
        return navx.getRawAccelY();
    }

    /**
     * Gets the current acceleration vector of the x-axis in g's
    */
    public double getX() {
        return navx.getRawAccelX();
    }

    //=========================================================================== 
    // widget methods
    //===========================================================================

    /**
     * Updates the value of the left encoder widget on shuffleboard
     * @param leftEncoderReading The current reading of the left encoder
     */
    public void printLeftEncoder(double leftEncoderReading) {
        leftEncoderWidget.setDouble(leftEncoderReading);
    }

    /**
     * Updates the value of the right encoder widget on shuffleboard
     * @param rightEncoderReading The current reading of the right encoder
     */
    public void printRightEncoder(double rightEncoderReading) {
        rightEncoderWidget.setDouble(rightEncoderReading);
    }

    //=========================================================================== 
    // periodic method
    //===========================================================================

    @Override
    public void periodic() {
        updateOdometry();
        driveScale = driveSpeeds.getSelected();
    }

}
