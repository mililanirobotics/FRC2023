package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//constants
import edu.wpi.first.wpilibj2.command.CommandBase;
//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class TravelDistanceCommand extends CommandBase {
    private double distance;
    private double initialDistance;
    private double travelDistance;
    private double percentPower;

    private DriveSubsystem m_driveSubsystem;   

    public TravelDistanceCommand(DriveSubsystem driveSubsystem, double distance, double percentPower, ShuffleboardTab motorTab) {
        m_driveSubsystem = driveSubsystem;
        this.percentPower = percentPower;
        this.distance = m_driveSubsystem.convertDistance(distance);
        this.initialDistance = m_driveSubsystem.getRightEncoder(); //using one motor to represent all
        this.travelDistance = this.distance + this.initialDistance;
        addRequirements(m_driveSubsystem);

        // motorTab.add("Initial Distance", initialDistance).withSize(2, 1).getEntry();
        // motorTab.add("Target Distance", travelDistance).withSize(2, 1).getEntry();
    }

    @Override
    public void initialize() {
        m_driveSubsystem.resetEncoders();
    }

    @Override
    public void execute() {
        m_driveSubsystem.printLeftEncoder(m_driveSubsystem.getLeftEncoder());
        m_driveSubsystem.printRightEncoder(m_driveSubsystem.getRightEncoder());

        m_driveSubsystem.drive(percentPower, percentPower);
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
