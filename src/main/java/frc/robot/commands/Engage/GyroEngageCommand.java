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
    private double error;
    private double percentPower;

    //constructor
    public GyroEngageCommand() {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void execute() {
        //gets the current angle of the gyro
        //calculates the difference between the current angle and [-1, 1]
        error = GameConstants.kChargingStationSlack - m_driveSubsystem.getAngle();

        percentPower = error * RobotConstants.kStationP;

        //limits the power of the motors
        if(Math.abs(percentPower) > 0.3) {
            percentPower = Math.copySign(0.3, percentPower);
        }

        m_driveSubsystem.drive(percentPower, percentPower);

        //used for debugging
        System.out.println("Current Angle: "+m_driveSubsystem.getAngle());
        System.out.println("Error: "+error);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Engage command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the erorr is within the range of (-1, 1)
        return Math.abs(error) < GameConstants.kChargingStationSlack; 
    }




}
