package frc.robot.commands.Drive;

//constants
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


//subsystems used
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class TravelDistanceCommand extends CommandBase {
    private double distance;
    private double initialDistance;
    private double travelDistance;
    private double percentPower;

    private DriveSubsystem m_driveSubsystem;    

    public TravelDistanceCommand(double distance, double percentPower) {
        m_driveSubsystem = RobotContainer.driveSubsystem;
        this.percentPower = percentPower;
        this.distance = m_driveSubsystem.convertDistance(distance);
        this.initialDistance = m_driveSubsystem.getRightEncoder(); //using one motor to represent all
        this.travelDistance = this.distance + this.initialDistance;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
        System.out.println("Initial Distance: "+initialDistance);
        RobotContainer.motorTab.add("Initial Distance", initialDistance);
        RobotContainer.motorTab.add("Travel Distance", travelDistance);

                        // speedSlider = Shuffleboard.getTab("Pre-match")
        //     .add("Max Speed", 1)
        //     .withWidget("Number Slider")
        //     .withSize(2, 2)
        //     .getEntry();
    }

    @Override
    public void execute() {
        RobotContainer.rightEncoderWidget.setDouble(m_driveSubsystem.getRightEncoder());
        RobotContainer.leftEncoderWidget.setDouble(m_driveSubsystem.getLeftEncoder());

        System.out.println("Current Position: "+m_driveSubsystem.getRightEncoder());
        System.out.println("Target Position: "+travelDistance);

        percentPower = m_driveSubsystem.drivePID.calculate(0.5); //michael special
        System.out.println("Michael's creation: "+percentPower);
        m_driveSubsystem.drive(percentPower, percentPower);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Final Count: "+m_driveSubsystem.getRightEncoder());
        m_driveSubsystem.shutdown();
    }

    @Override 
    public boolean isFinished() {
        return m_driveSubsystem.getRightEncoder() >= travelDistance && m_driveSubsystem.getRightEncoder() >= travelDistance;
    }
}
