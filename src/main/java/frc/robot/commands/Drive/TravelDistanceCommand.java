package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//constants
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.RobotConstants;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class TravelDistanceCommand extends CommandBase {
    private double distance;
    private double initialDistance;
    private double travelDistance;
    private double percentPower;

    private DriveSubsystem m_driveSubsystem; 
    private ShuffleboardTab encoderDriveTab = Shuffleboard.getTab("Encoder Drive");
  

    private GenericEntry powerWidget;
    private PIDController encoderDrivePID = new PIDController(RobotConstants.kEncoderDriveP, RobotConstants.kEncoderDriveI, RobotConstants.kEncoderDriveD);

    public TravelDistanceCommand(double distance, double percentPower, DriveSubsystem driveSubsystem) {
        m_driveSubsystem = driveSubsystem;
        this.percentPower = percentPower;
        this.distance = m_driveSubsystem.convertDistance(distance);
        this.initialDistance = m_driveSubsystem.getRightEncoder(); //using one motor to represent all
        this.travelDistance = this.distance + this.initialDistance;
        powerWidget = encoderDriveTab.add("Power", 0).withSize(4, 4).getEntry();
        encoderDriveTab.add("Encoder Drive PID", encoderDrivePID).withSize(4, 4);
        addRequirements(m_driveSubsystem);

        // motorTab.add("Initial Distance", initialDistance).withSize(2, 1).getEntry();
        // motorTab.add("Target Distance", travelDistance).withSize(2, 1).getEntry();
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
        System.out.println("Initial Distance: "+initialDistance);


                        // speedSlider = Shuffleboard.getTab("Pre-match")
        //     .add("Max Speed", 1)
        //     .withWidget("Number Slider")
        //     .withSize(2, 2)
        //     .getEntry();

    }

    @Override
    public void execute() {
        System.out.println("Current Position: "+m_driveSubsystem.getRightEncoder());
        System.out.println("Target Position: "+travelDistance);

        percentPower = encoderDrivePID.calculate(m_driveSubsystem.getRightEncoder(), travelDistance);
        percentPower = RobotContainer.limitValue(percentPower, 0.5, 0.2); // Will change percent power to grab from PID controllers.

        m_driveSubsystem.drive(percentPower, percentPower);

        powerWidget.setDouble(percentPower);
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
