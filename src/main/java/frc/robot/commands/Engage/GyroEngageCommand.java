package frc.robot.commands.Engage;

//subsystems and commands 
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;

public class GyroEngageCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring measured variables
    private double error;
    private int iteration;

    //declaring PID controller
    private PIDController engagePID;

    //declaring shuffleboard widgets
    private GenericEntry pitchAngleWidget;
    private GenericEntry errorAngleWidget;
    private GenericEntry powerWidget;

    //constructor
    public GyroEngageCommand(DriveSubsystem driveSubsystem, ShuffleboardTab engagementTab) {
        //initializes PID controller
        engagePID = new PIDController(RobotConstants.kStationP, RobotConstants.kStationI, RobotConstants.kStationD);
        engagePID.setTolerance(2);

        //initializes subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        //initializes widgets
        pitchAngleWidget = engagementTab.add("Pitch Angle", 0).withSize(2, 1).getEntry();
        engagementTab.add("Target Angle", GameConstants.kChargingStationSlack).withSize(2, 1).getEntry();
        errorAngleWidget =  engagementTab.add("Error", 0).withSize(2, 1).getEntry();  
        powerWidget = engagementTab.add("Power", 0).withSize(2, 1).getEntry();  
    }

    @Override
    public void initialize() {  
        iteration = 0; //acts as a timer
    }

    @Override
    public void execute() {
        //gets the current angle and uses it to calculate an adjusted speed using the PID controller
        double currentAngle = m_driveSubsystem.getPitch();
        double percentPower = engagePID.calculate(currentAngle, 0);

        //increases the speed when going backwards to overcome friction and gravity
        if(percentPower < 0) {
            percentPower *= 1.2;
        }

        //limits the motor's power to a max of 0.4
        if(Math.abs(percentPower) > 0.4) {
            percentPower = 0.4;
        }

        //setting the power of the motors
        m_driveSubsystem.drive(percentPower, percentPower);

        //if the robot is considered in the "balanceing" range, it starts the timer
        //resets the timer if the if statement condition is not met
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
        //finishes the method if the robot is balanced for 1 second
        return iteration >= 50;
    }
}
