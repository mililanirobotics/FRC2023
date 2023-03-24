package frc.robot.commands.Drive;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//general imports
import frc.robot.Constants.DriveConstants;
//constants
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.ArmConstants;

//constructor
public class LimelightTravelDistanceCommand extends CommandBase {
    //declaring subsystems
    private LimelightSubsystem m_limelightSubsystem;
    private DriveSubsystem m_driveSubsystem;
    //PID Controller
    private PIDController limelightTravelPID;
    //declaring measured variables
    private double travelError;
    private double targetHeight;
    private LimelightSubsystem.Pipeline pipeline;
    //timer
    private int iteration;
    //declaring shuffleboard tabs
    private static GenericEntry travelDistanceWidget;
    private static GenericEntry leftDrivePower;
    private static GenericEntry rightDrivePower;
    
    //constructor
    public LimelightTravelDistanceCommand(LimelightSubsystem.Pipeline pipeline, double targetHeight, LimelightSubsystem limelightSubsystem, DriveSubsystem driveSubsystem, ShuffleboardTab limelightTab) {
        //initializing subsystems
        m_limelightSubsystem = limelightSubsystem;
        m_driveSubsystem = driveSubsystem;
        
        //initializing measured variables
        this.targetHeight = targetHeight;
        this.pipeline = pipeline;

        limelightTravelPID = new PIDController(DriveConstants.kLimelightTravelP, DriveConstants.kLimelightTravelI, DriveConstants.kLimelightTravelD);

        addRequirements(m_limelightSubsystem, m_driveSubsystem);

        if(travelDistanceWidget == null) {
            travelDistanceWidget = limelightTab.add("Limelight travel distance", 0).withSize(2, 1).getEntry();
            leftDrivePower = limelightTab.add("Left Power", 0).withSize(2, 1).getEntry();
            rightDrivePower = limelightTab.add("Right Power", 0).withSize(2, 1).getEntry();
        }
    }

    @Override
    public void initialize() {
        m_limelightSubsystem.setPipeline(pipeline);
        iteration = 0;
    }

    @Override
    public void execute() {
        if(iteration > LimelightConstants.kSwitchDelay) {
            //calculating the distance you still need to travel to reach your target distance away from the node
            travelError = m_limelightSubsystem.getDepth(targetHeight) - ArmConstants.kArmReach; 
            System.out.println(travelError);

            //calculating the adjusted speed using the encoder drive PID controller
            double percentPower = -limelightTravelPID.calculate(travelError, 0);

            //updates the travel distance widget on shuffleboard
            travelDistanceWidget.setDouble(travelError);

            leftDrivePower.setDouble(percentPower);
            rightDrivePower.setDouble(percentPower);

            //limits the power of the drive speed
            if(percentPower > 0.4) {
                percentPower = Math.copySign(0.4, percentPower);
            } 

            //setting the current power of the drive
            m_driveSubsystem.drive(percentPower, percentPower);
            iteration++;
        }
        else {
            iteration++;
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Travelling to grid has finished");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(travelError) < 1 && iteration > LimelightConstants.kMinimumTime;
    }
}
