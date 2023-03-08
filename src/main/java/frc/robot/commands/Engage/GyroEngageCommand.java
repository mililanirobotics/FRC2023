package frc.robot.commands.Engage;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;

public class GyroEngageCommand extends CommandBase {
    //instance variables
    private DriveSubsystem m_driveSubsystem;
    private double error;
    private int iteration;

    private PIDController engagePID;

    private GenericEntry pitchAngleWidget;
    private GenericEntry errorAngleWidget;
    private GenericEntry powerWidget;

    //constructor
    public GyroEngageCommand(DriveSubsystem driveSubsystem, ShuffleboardTab engagementTab) {
        engagePID = new PIDController(RobotConstants.kStationP, RobotConstants.kStationI, RobotConstants.kStationD);
        engagePID.setTolerance(2);
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        pitchAngleWidget = engagementTab.add("Pitch Angle", 0).withSize(2, 1).getEntry();
        engagementTab.add("Target Angle", GameConstants.kChargingStationSlack).withSize(2, 1).getEntry();
        errorAngleWidget =  engagementTab.add("Error", 0).withSize(2, 1).getEntry();  
        powerWidget = engagementTab.add("Power", 0).withSize(2, 1).getEntry();  
    }

    @Override
    public void initialize() {  
        iteration = 0;
    }

    @Override
    public void execute() {
        double currentAngle = m_driveSubsystem.getPitch();
        double percentPower = engagePID.calculate(currentAngle, 0);

        if(percentPower < 0) {
            percentPower *= 1.2;
        }

        if(Math.abs(percentPower) > 0.4) {
            percentPower = 0.4  ;
        }

        m_driveSubsystem.drive(percentPower, percentPower);

        if(Math.abs(currentAngle) < GameConstants.kChargingStationSlack) {
            iteration++;
        }
        else {
            iteration = 0;
        }

        //debugging statements
        pitchAngleWidget.setDouble(currentAngle);
        errorAngleWidget.setDouble(error);
        powerWidget.setDouble(percentPower);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Engage command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the erorr is within the range of (-3, 3)
        return iteration >= 50;
    }
}
