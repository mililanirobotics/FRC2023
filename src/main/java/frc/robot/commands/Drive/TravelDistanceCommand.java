package frc.robot.commands.Drive;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
import edu.wpi.first.networktables.GenericEntry;

public class TravelDistanceCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem; 

    //declaring the measured variables
    private double distance;
    private double initialDistance;
    private double travelDistance;

    //declaring shuffleboard tabs
    private GenericEntry powerWidget;
    private GenericEntry distanceWidget;

    //constructor
    public TravelDistanceCommand(double distance, DriveSubsystem driveSubsystem, ShuffleboardTab motorTab) {
        //initializing the recorded data
        this.distance = m_driveSubsystem.convertDistance(distance);
        this.initialDistance = m_driveSubsystem.getRightEncoder(); //using one motor to represent all
        this.travelDistance = this.distance + this.initialDistance;

        //initializing subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        //initializing shuffleboard widgets
        powerWidget = motorTab.add("Power", 0).withSize(4, 4).getEntry();
        distanceWidget = motorTab.add("Current Distance", 0).withSize(2, 1).getEntry();

        //displays the data on shuffleboard
        motorTab.add("Initial Distance", initialDistance).withSize(2, 1).getEntry();
        motorTab.add("Target Distance", travelDistance).withSize(2, 1).getEntry();
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
        System.out.println("Initial Distance: "+initialDistance);
    }

    @Override
    public void execute() {
        //calculates the adjusted percent power based on hte encoder drive PID controller
        double percentPower = m_driveSubsystem.encoderPIDSpeed(m_driveSubsystem.getRightEncoder(), travelDistance);
        percentPower = RobotContainer.limitSpeed(percentPower, 0.3, 0.5); 

        //setting the power of the motors
        m_driveSubsystem.drive(percentPower, percentPower);

        //updating widgets
        powerWidget.setDouble(percentPower);
        distanceWidget.setDouble(distance);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Final Count: "+m_driveSubsystem.getRightEncoder());
        m_driveSubsystem.shutdown();
    }

    @Override 
    public boolean isFinished() {
        return m_driveSubsystem.getLeftEncoder() >= travelDistance && m_driveSubsystem.getRightEncoder() >= travelDistance;
    }
}
