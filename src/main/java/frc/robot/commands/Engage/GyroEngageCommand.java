package frc.robot.commands.Engage;

import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.RobotContainer;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class GyroEngageCommand extends CommandBase {
    //instance variables
    private DriveSubsystem m_driveSubsystem;
    private double currentAngle;
    private double error;
    private double percentPower;
    private int iteration;

    
    //encoder add-on  
    // private double rightEncoderReading;
    // private double leftEncoderReading;
    // private double intervalStart;
    // private double intervalEnd;

    //constructor
    public GyroEngageCommand() {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    //checks to see if the encoder readings are within the balancing range
    // public boolean checkEncoders() {
    //     return (rightEncoderReading > intervalStart && rightEncoderReading < intervalEnd)
    //             && (leftEncoderReading > intervalStart && leftEncoderReading < intervalEnd);
    // }

    @Override
    public void initialize() {  
        iteration = 0;
    }

    @Override
    public void execute() {
        currentAngle = m_driveSubsystem.getPitch();

        //error is based on how far the current angle is from 0, which is considered "balanced"
        // error = -currentAngle;

        percentPower = m_driveSubsystem.engagePID.calculate(currentAngle, 0);
        // percentPower = error * RobotConstants.kStationP;
        if(percentPower < 0) {
            percentPower *= 1.25;
        }

        if(Math.abs(percentPower) > 0.45) {
            percentPower = 0.45;
        }

        m_driveSubsystem.drive(percentPower, percentPower);

        if(Math.abs(currentAngle) < GameConstants.kChargingStationSlack) {
            iteration++;
        }
        else {
            iteration = 0;
        }

        

        //debugging statements
        RobotContainer.pitchAngleWidget.setDouble(currentAngle);
        RobotContainer.errorAngleWidget.setDouble(error);
        RobotContainer.powerWidget.setDouble(percentPower);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Engage command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the erorr is within the range of (-2, 2)
        return iteration >= 50;
    }
}
