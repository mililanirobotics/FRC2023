package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//constants
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotConstants; 

public class DriveSubsystem extends SubsystemBase {
    //declaring drive motors, motor controller groups, and encoders that control the drive
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

    private DifferentialDrive drive;

    //feedforward and general drive PID
    private SimpleMotorFeedforward feedforward;
    private PIDController drivePID;
    private PIDController encoderDrivePID;

    //declaring the gyro
    private final AHRS navx;

    //sendable chooser for adjustable drive speeds
    private SendableChooser<Double> driveSpeeds = new SendableChooser<Double>();
    private double driveScale;

    //widgets to track the current encoder readings of the drive
    private GenericEntry leftEncoderWidget;
    private GenericEntry rightEncoderWidget;
    //widget to track the current power of the motors
    private GenericEntry leftDrivePower;
    private GenericEntry rightDrivePower;

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
        rightFront.setInverted(DriveConstants.kRightFrontReverse);
        rightBack.setInverted(DriveConstants.kRightBackReverse);

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
        leftBackEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);
        rightFrontEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);
        rightBackEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);

        //resetting encoders upon object creation
        resetEncoders(); 

        //initializing and enabling navx
        navx = new AHRS(I2C.Port.kMXP);
        navx.enableLogging(true);

        //initializing differential drive (tank drive) object
        drive = new DifferentialDrive(leftDrive, rightDrive);

        //initializing the PID and feedforward used for the drive (in the subsystem because it's used in multiple classes)
        drivePID = new PIDController(RobotConstants.kDriveP, RobotConstants.kDriveI, RobotConstants.kDriveD);
        feedforward = new SimpleMotorFeedforward(RobotConstants.kDriveS, RobotConstants.kDriveV , RobotConstants.kDriveA);
        encoderDrivePID = new PIDController(RobotConstants.kEncoderDriveP, RobotConstants.kEncoderDriveI, RobotConstants.kEncoderDriveD);

        //adding shuffleboard widgets to the "motor tab"
        leftEncoderWidget = motorTab.add("Left Encoder", 0).withSize(2, 1).getEntry();
        rightEncoderWidget = motorTab.add("Right Encoder", 0).withSize(2, 1).getEntry(); 
        leftDrivePower = motorTab.add("Left Drive Power", 0).withSize(2, 1).getEntry();
        rightDrivePower = motorTab.add("Right Drive Power", 0).withSize(2, 1).getEntry();

        //adding the various drive speed multiplyers to shuffleboard
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
     * @param rightSpeed controls the speed of the right drive motors
     * @param leftSpeed controls the speed of the left drive motors
    */
    public void drive(double leftPercentPower, double rightPercentPower) {
        drive.tankDrive(leftPercentPower, rightPercentPower, true);
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

    /**
     * Returns the calculate speed of your drive motors during encoder drive
     * Based on the error from your setpoint (in counts)
     * The PID controller is inside the drive subsystem because it's used in all encoder drive applications
     * @param currentValue The current value of your encoder
     * @param setpoint The value your encoder is trying to approach
     * @return The adjusted speed from the PID controller
     */
    public double encoderPIDSpeed(double currentValue, double setpoint) {
        return encoderDrivePID.calculate(currentValue, setpoint);
    }

    /**
     * Sets all of the drive motors to 0 power
     */
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
     * Resets all of the drive encoder values
    */
    public void resetEncoders() {
        leftFrontEncoder.setPosition(0);
        leftBackEncoder.setPosition(0);
        rightFrontEncoder.setPosition(0);
        rightBackEncoder.setPosition(0);
    }

    /**
     * Returns current counts of the left drive encoder
     * @return The current counts of the left drive encoder
    */
    public double getLeftEncoder() {
        return leftFrontEncoder.getPosition();
    }

    /**
     * Returns current counts of the right drive encoder
     * @return The current counts of the right drive encoder
    */
    public double getRightEncoder() {
        return rightFrontEncoder.getPosition();
    }

    /**
     * Converts the inputed inches into counts for the encoder to read
     * @param inches The total amount of inches that will be converted
     * @return The total amount of counts the encoder should read 
     */
    public double convertDistance(double inches) {
        return inches * 23.4192;
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

    /**
     * Updates the value of the left drive power widget on shuffleboard
     * @param power The current percent power of the left drive motor
     */
    public void printLeftPower(double power) {
        leftDrivePower.setDouble(power);
    }

    /**
     * Updates the value of the right drive power widget on shuffleboard
     * @param power The current percent power of the right drive motor
     */
    public void printRightPower(double power) {
        rightDrivePower.setDouble(power);
    }

    //=========================================================================== 
    // periodic method
    //===========================================================================

    @Override
    public void periodic() {
        driveScale = driveSpeeds.getSelected();
    }

}