package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class AlignmentCommand extends CommandBase {
    
    private LimelightSubsystem m_Limelightsubsystem;
    private DriveSubsystem m_DriveSubsystem;
    private double offsetAngle;
    private double speed;
    private LimelightSubsystem.Pipeline pipeline;


    public AlignmentCommand(LimelightSubsystem.Pipeline pipeline, LimelightSubsystem limelightSubsystem) {
        m_Limelightsubsystem = limelightSubsystem;
        m_DriveSubsystem = RobotContainer.driveSubsystem;
        speed = 0;
        this.pipeline = pipeline;
        addRequirements(m_DriveSubsystem, m_Limelightsubsystem);
    }

    @Override
    public void initialize() {
        m_Limelightsubsystem.setPipeline(pipeline);
    }

    @Override
    public void execute() {
        offsetAngle = m_Limelightsubsystem.getHorizontalOffset();
  
        speed = offsetAngle * LimelightConstants.kPAlignAngle;
        if (Math.abs(speed) > 0.35) {
        speed = Math.copySign(0.35, speed);
        }
        else if (Math.abs(speed) < 0.35) {
        speed = Math.copySign(0.35, speed);
        }
        m_DriveSubsystem.drive(speed, -speed);

        System.out.println("Execute running");
    }    
    
    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending angle: "+offsetAngle);
        System.out.println("Alignment has finished");
        m_DriveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(offsetAngle) < 0.5;
    }
}
