package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotContainer;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotConstants; 


public class DriveSubsystem extends SubsystemBase {
    //declaring drive motors and encoders
    private CANSparkMax leftFront;
    private CANSparkMax leftBack;
    private CANSparkMax rightFront;
    private CANSparkMax rightBack;

    private MotorControllerGroup leftDrive;
    private MotorControllerGroup rightDrive;

    private RelativeEncoder rightBackEncoder;
    private RelativeEncoder rightFrontEncoder;
    private RelativeEncoder leftFrontEncoder;
    private RelativeEncoder leftBackEncoder;

    public Timer timer;

    //declaring differential drive Odometry and Kinematics objects
    private DifferentialDriveOdometry odometry;
    //private DifferentialDriveKinematics kinematics;
    public DifferentialDrive drive;


    //PID Controllers
    public PIDController engagePID = new PIDController(RobotConstants.kStationP, RobotConstants.kStationI, RobotConstants.kStationD);
    public PIDController drivePID = new PIDController(RobotConstants.kDriveP, RobotConstants.kDriveI, RobotConstants.kDriveD);

    //initializing the gyro
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    AHRS navx = new AHRS(I2C.Port.kMXP);

    //initializing 3-axis accelorometer
    Accelerometer accelerometer = new BuiltInAccelerometer();

    //Sendable chooser for drive speeds
    SendableChooser<Double> driveSpeeds = new SendableChooser<Double>();
    public double driveScale;

    //Sendable chooser for which access the Accelerometer should access
    SendableChooser<Boolean> axisChooser = new SendableChooser<Boolean>();
    public boolean axisChoice;
    
    //constructor
    //initializes all motors, motor controllers, drive trains, and dashboard options
    public DriveSubsystem() {
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

        //creating a timer object
        timer = new Timer();

        navx.enableLogging(true);

        engagePID.setTolerance(Math.PI / 180);

        //creating odometry and differential drive (tank drive) objects
        odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), getLeftEncoder(), getRightEncoder());
        drive = new DifferentialDrive(leftDrive, rightDrive);

        //setting options for the user to choose the drive speed
        driveSpeeds.setDefaultOption("100%", 1.0);
        driveSpeeds.addOption("50%", 0.5);
        driveSpeeds.addOption("35%", 0.35);
        RobotContainer.preMatchTab.add("Max Speed", driveSpeeds);

        //setting options for the user to choose which axis the accelerometer refers to
        axisChooser.setDefaultOption("Z-axis", true);
        axisChooser.addOption("Y-axis", false);
        RobotContainer.preMatchTab.add("Axis Selected", axisChooser);
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

    public void shutdown() {
        drive.tankDrive(0, 0);
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
    // periodic method
    //===========================================================================

    @Override
    public void periodic() {
        updateOdometry();
        driveScale = driveSpeeds.getSelected();
        axisChoice = axisChooser.getSelected();
    }

}
