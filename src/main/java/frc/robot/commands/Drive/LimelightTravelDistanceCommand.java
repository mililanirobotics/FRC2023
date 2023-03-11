package frc.robot.commands.Drive;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//general imports
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.LimelightConstants;

//constructor
public class LimelightTravelDistanceCommand extends CommandBase {
    //declaring subsystems
    private LimelightSubsystem m_limelightSubsystem;
    private DriveSubsystem m_driveSubsystem;
    
    //declaring measured variables
    private double travelDistance;
    private double targetHeight;

    //declaring shuffleboard tabs
    private GenericEntry travelDistanceWidget;
    
    //constructor
    public LimelightTravelDistanceCommand(double targetHeight, LimelightSubsystem limelightSubsystem, DriveSubsystem driveSubsystem, ShuffleboardTab motorTab) {
        //initializing subsystems
        m_limelightSubsystem = limelightSubsystem;
        m_driveSubsystem = driveSubsystem;
        
        this.targetHeight = targetHeight;
        addRequirements(m_limelightSubsystem, m_driveSubsystem);

        travelDistanceWidget = motorTab.add("Limelight travel distance", 0).withSize(2, 1).getEntry();
    }

    @Override
    public void execute() {
        //calculating the distance you still need to travel to reach your target distance away from the node
        travelDistance = m_limelightSubsystem.getDepth(targetHeight) - LimelightConstants.kArmReach; 
        
        //calculating the adjusted speed using the encoder drive PID controller
        double percentPower = m_driveSubsystem.encoderPIDSpeed(travelDistance, 0);

        //updates the travel distance widget on shuffleboard
        travelDistanceWidget.setDouble(travelDistance);

        //limits the power of the drive speed 
        percentPower = RobotContainer.limitSpeed(percentPower, 0.3, 0.5);
        
        //setting the current power of the drive
        m_driveSubsystem.drive(percentPower, percentPower);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Travelling to grid has finished");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(travelDistance) < 1;
    }
}
