package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.RobotContainer;



//subsystems used
import frc.robot.subsystems.DriveSubsystem;


public class GyroTurnCommand extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private double turnDegrees;
    private double targetAngle;
    private double error;

    public GyroTurnCommand(double turnDegrees) {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        addRequirements(m_driveSubsystem);
        this.turnDegrees = turnDegrees;
    }

    @Override
    public void initialize() {
        targetAngle = m_driveSubsystem.getYaw() + turnDegrees;
    }

    @Override
    public void execute() {
        error = targetAngle - m_driveSubsystem.getYaw();

        //scaling power based on the degree of error
        double power = error * RobotConstants.kTurnP;

        //speed appears iffy 
        //retains the sign of the original power double
        //assigns the max/min power if it goes over or under
        if(Math.abs(power) > 0.6) {
            power = Math.copySign(0.6, power);
        } 
        else if (Math.abs(power) < 0.45) {
            power = Math.copySign(0.45, power);
        }

        //if error is +, left speed + and right speed - (turns clockwise)
        //if error is -, left speed - and right speed + (turns counterclockwise)
        m_driveSubsystem.drive(power, -power);

        //debuggin statements
        System.out.println("Power: "+power);
        System.out.println("Current angle: "+Math.round(m_driveSubsystem.getYaw()));
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Turn command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //ends the command if the error is within the desired range
        return Math.round(Math.abs(error)) < GameConstants.kAlignmentSlack;
    }

}
