package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;
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
    public final CANSparkMax rightFront;
    public final CANSparkMax rightBack;

    private final MotorControllerGroup leftDrive;
    private final MotorControllerGroup rightDrive;

    private final RelativeEncoder rightBackEncoder;
    private final RelativeEncoder rightFrontEncoder;
    private final RelativeEncoder leftFrontEncoder;
    private final RelativeEncoder leftBackEncoder;

    //declaring the gyro
    private final AHRS navx;

    //sendable chooser for adjustable drive speeds
    private SendableChooser<Double> driveSpeeds;
    private double driveScale;

    //widgets to track the current encoder readings of the drive
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
        rightFront.setInverted(DriveConstants.kRightFrontReverse);
        rightBack.setInverted(DriveConstants.kRightBackReverse);

        leftFront.setSmartCurrentLimit(65, 20);
        leftBack.setSmartCurrentLimit(65, 20);
        rightFront.setSmartCurrentLimit(65, 20);
        rightBack.setSmartCurrentLimit(65, 20);

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

        //adding shuffleboard widgets to the "motor tab"
        leftEncoderWidget = motorTab.add("Left Encoder", 0).withSize(2, 1).getEntry();
        rightEncoderWidget = motorTab.add("Right Encoder", 0).withSize(2, 1).getEntry(); 
        
        //adding the various drive speed multiplyers to shuffleboard
        driveSpeeds = new SendableChooser<Double>();

        driveSpeeds.setDefaultOption("100%", 1.0);
        driveSpeeds.addOption("50%", 0.5);
        driveSpeeds.addOption("60%", 0.6);
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
        leftDrive.setVoltage(leftPercentPower * 12);
        rightDrive.setVoltage(rightPercentPower * 12);
    }

    /**
     * Sets all of the drive motors to 0 power
     */
    public void shutdown() {
        leftDrive.setVoltage(0);
        rightDrive.setVoltage(0);
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
        return inches * ((RobotConstants.kCountsPerRev * RobotConstants.kGearRatio) / (RobotConstants.kWheelCircumference));
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
        driveScale = driveSpeeds.getSelected();
    }

}