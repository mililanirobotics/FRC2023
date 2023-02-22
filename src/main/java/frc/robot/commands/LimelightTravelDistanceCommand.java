package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class LimelightTravelDistanceCommand extends CommandBase{
    private LimelightSubsystem m_LimelightSubsystem;
    private DriveSubsystem m_DriveSubsystem;
    private double speed;
    private double travelDistance;
    private double targetHeight;


    public LimelightTravelDistanceCommand (double targetHeight) {
        m_LimelightSubsystem = RobotContainer.limelightSubsystem;
        m_DriveSubsystem = RobotContainer.driveSubsystem;
        speed = 0;
        this.targetHeight = targetHeight;
        addRequirements(m_LimelightSubsystem, m_DriveSubsystem);
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        travelDistance = m_LimelightSubsystem.getDepth(targetHeight) - LimelightConstants.kArmReach; // subtracting limelight's distance by claw's reach, subject to change

        SmartDashboard.putNumber("Vertical Offset", m_LimelightSubsystem.getVerticalOffset());
        SmartDashboard.putNumber("Depth to Target", m_LimelightSubsystem.getDepth(targetHeight));
        SmartDashboard.updateValues();

        double kPAlignDistance = 0.01;
        if (travelDistance < -2 || travelDistance > 2) {
            speed = travelDistance * kPAlignDistance;
            if (Math.abs(speed) > 0.15) {
                speed = Math.copySign(0.15, speed);
            }
            else if (Math.abs(speed) < 0.10) {
                speed = Math.copySign(0.10, speed);
            }
            m_DriveSubsystem.drive(speed, -speed);
        }
        else {
            m_DriveSubsystem.shutdown();
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Travelling to grid has finished");
        m_DriveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return(travelDistance < 2 || travelDistance > -2);
    }
}
