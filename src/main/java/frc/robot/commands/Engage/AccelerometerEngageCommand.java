package frc.robot.commands.Engage;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.GameConstants;
import frc.robot.RobotContainer;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class AccelerometerEngageCommand extends CommandBase {
    //instance variables
    private DriveSubsystem m_driveSubsystem;

    private double error;
    private double percentPower;
    private double rightEncoderReading;
    private double leftEncoderReading;
    private double startingAngle;
    private double currAngle;

    private double intervalStart;
    private double intervalEnd;

    //constructor
    public AccelerometerEngageCommand() {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        intervalStart = m_driveSubsystem.convertDistance(GameConstants.kChargeStationIntervalStart);
        intervalEnd = m_driveSubsystem.convertDistance(GameConstants.kChargeStationIntervalEnd);
        addRequirements(m_driveSubsystem);
    }

    //checks to see if the encoder readings are within the balancing range
    public boolean checkEncoders() {
        return (rightEncoderReading > intervalStart && rightEncoderReading < intervalEnd)
               && (leftEncoderReading > intervalStart && leftEncoderReading < intervalEnd);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
        startingAngle = Math.atan(m_driveSubsystem.getY() / m_driveSubsystem.getZ());
        m_driveSubsystem.timer.reset();
    }

    @Override
    public void execute() {
        double zAcceleration = m_driveSubsystem.getZ();
        double yAcceleration = m_driveSubsystem.getY();

        rightEncoderReading = m_driveSubsystem.getRightEncoder();
        leftEncoderReading = m_driveSubsystem.getLeftEncoder();

        //gets the current error using the perpendicular acceleration vector (measured in g's)
        //in respect to the robot, and the constant acceleration due to gravity 
        //calculates the difference between the current angle and [-1, 1]
        if(m_driveSubsystem.axisChoice) {
            currAngle = Math.atan(yAcceleration / zAcceleration);
        }
        else {
            currAngle = Math.atan(zAcceleration / yAcceleration);
        }

        error = currAngle - GameConstants.kChargingStationSlack;

        percentPower = m_driveSubsystem.engagePID.calculate(Math.abs(currAngle), GameConstants.kAlignmentSlack);

        System.out.println("PID Output: "+percentPower);

        //limits the power of the motors
        if(Math.abs(percentPower) > 0.4) {
            percentPower = Math.copySign(0.4, percentPower);
        }
        else if(Math.abs(percentPower) < 0.29) {
            percentPower = Math.copySign(0.29, percentPower);
        }

        //checks to see if kevin is within the stopping distance
        // if(Math.abs(currAngle) <= startingAngle - GameConstants.kStopAngleRad) {
        //     percentPower = 0;
        // }

        m_driveSubsystem.drive(0.29, 0.29);

        //starts a timer to see if kevin remains balanced
        if(Math.abs(error) < GameConstants.kChargingStationSlackRad && checkEncoders()) {
            m_driveSubsystem.timer.start();
        }
        else {
            m_driveSubsystem.timer.reset();
        }

        System.out.println("Accelerometer Z: "+zAcceleration);
        System.out.println("Current Angle: "+Math.atan(yAcceleration / zAcceleration));
        System.out.println("Error: "+error);
        System.out.println("Target Angle: "+GameConstants.kChargingStationSlack);
        System.out.println("Power: "+percentPower);
        System.out.println("Stopping angle: "+(startingAngle - GameConstants.kStopAngle));
        System.out.println("Stopping condition: "+(Math.abs(currAngle) <= startingAngle - GameConstants.kStopAngle));
        System.out.println("Right Encoder reading: "+rightEncoderReading);
        System.out.println("Left Encoder reading: "+leftEncoderReading);
        System.out.println("\n");
        SmartDashboard.updateValues();

    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Is done", true);
        SmartDashboard.updateValues();
        System.out.println("Engage command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the erorr is within the range of (-1, 1)
        return m_driveSubsystem.timer.get() >= 3;
    }
}
