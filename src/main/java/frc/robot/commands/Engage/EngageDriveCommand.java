package frc.robot.commands.Engage;

//subsystems and commands 
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.EngagementConstants;
//constants
import frc.robot.Constants.RobotConstants;

public class EngageDriveCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring measured variables
    private double initialAngle;

    //constructor
    public EngageDriveCommand(DriveSubsystem driveSubsystem, ShuffleboardTab engagementTab) {
        //initializes subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {  
        System.out.println("Yes");
        initialAngle = m_driveSubsystem.getPitch() + RobotConstants.kPitchOffset;
        
        m_driveSubsystem.drive(0.4, 0.4);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Engage Drive Command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the robot is balanced for 1 second
        return Math.abs(m_driveSubsystem.getPitch()) > Math.abs(initialAngle) + 10;
    }
}
