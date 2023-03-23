package frc.robot.commands.Drive;

import frc.robot.Constants.DriveConstants;
//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;

public class TravelDistanceCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem; 

    //PID Controller
    private PIDController encoderDrivePID;

    //declaring the measured variables
    private double distance;
    private double initialDistance;
    private double travelDistance;

    //declaring shuffleboard tabs
    private static GenericEntry distanceWidget;
    private static GenericEntry leftDrivePower;
    private static GenericEntry rightDrivePower;

    //constructor
    public TravelDistanceCommand(double distance, DriveSubsystem driveSubsystem, ShuffleboardTab motorTab) {
        //initializing subsystems
        m_driveSubsystem = driveSubsystem;

        //initializing the recorded data
        this.distance = m_driveSubsystem.convertDistance(distance);
        this.initialDistance = m_driveSubsystem.getRightEncoder(); //using one motor to represent all
        this.travelDistance = this.distance + this.initialDistance;

        encoderDrivePID = new PIDController(DriveConstants.kEncoderDriveP, DriveConstants.kEncoderDriveI, DriveConstants.kEncoderDriveD);

        addRequirements(m_driveSubsystem);

        if(distanceWidget == null) {
            //initializing shuffleboard widgets
            distanceWidget = motorTab.add("Current Distance", 0).withSize(2, 1).getEntry();
            leftDrivePower = motorTab.add("Left Drive Power", 0).withSize(2, 1).getEntry();
            rightDrivePower = motorTab.add("Right Drive Power", 0).withSize(2, 1).getEntry();
    
            //displays the data on shuffleboard
            motorTab.add("Initial Distance", initialDistance).withSize(2, 1).getEntry();
            motorTab.add("Target Distance", travelDistance).withSize(2, 1).getEntry();
        }
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
        System.out.println("Initial Distance: "+initialDistance);
    }

    @Override
    public void execute() {
        //calculates the adjusted percent power based on hte encoder drive PID controller
        double percentPower = encoderDrivePID.calculate(m_driveSubsystem.getRightEncoder(), travelDistance);

        if(Math.abs(percentPower) > 0.5) {
            percentPower = Math.copySign(0.5, percentPower);
        }

        //setting the power of the motors
        m_driveSubsystem.drive(percentPower, percentPower);

        //updating widgets
        leftDrivePower.setDouble(percentPower);
        rightDrivePower.setDouble(percentPower);
        distanceWidget.setDouble(distance);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Travel distance: "+travelDistance);
        System.out.println("Final Count: "+encoderDrivePID.getPositionError());
        m_driveSubsystem.shutdown();
    }

    @Override 
    public boolean isFinished() {
        return Math.abs(encoderDrivePID.getPositionError()) < m_driveSubsystem.convertDistance(1);
    }
}
