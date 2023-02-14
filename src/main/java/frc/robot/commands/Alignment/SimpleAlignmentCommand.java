package frc.robot.commands.Alignment;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.GameConstants;
//constants
import frc.robot.Constants.RobotConstants;
import frc.robot.RobotContainer;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class SimpleAlignmentCommand extends CommandBase {
    //subsystems used
    private DriveSubsystem m_driveSubsystem;
    private LimelightSubsystem m_limelightSubsystem;
    //key variables
    private double offset;

    public SimpleAlignmentCommand() {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        m_limelightSubsystem = RobotContainer.limelightSubsystem;
        addRequirements(m_driveSubsystem, m_limelightSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        offset = m_limelightSubsystem.getHorizontalOffset();
        double percentPower = offset * RobotConstants.kTurnP;

        if(Math.abs(percentPower) > 0.45) {
            percentPower = Math.copySign(0.45, percentPower);
        }
        else if(Math.abs(percentPower) < 0.3) {
            percentPower = Math.copySign(0.3, percentPower);
        }

        m_driveSubsystem.drive(percentPower, -percentPower);

        System.out.println("Drive Speed: "+percentPower);
        System.out.println("offset: "+offset);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Simple alignment command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(offset) < GameConstants.kAlignmentSlack;
    }
}
