package frc.robot.commands.Engage;

//subsystems and commands 
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//constants
import frc.robot.Constants.RobotConstants;
import frc.robot.Constants.EngagementConstants;

public class GyroEngageCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring measured variables
    private double error;
    private int iteration;
    private double initialAngle;

    //declaring PID controller
    private PIDController engagePID;

    //declaring shuffleboard widgets
    private static GenericEntry pitchAngleWidget;
    private static GenericEntry errorAngleWidget;
    private static GenericEntry powerWidget;

    //constructor
    public GyroEngageCommand(DriveSubsystem driveSubsystem, ShuffleboardTab engagementTab) {
        //initializes PID controller
        engagePID = new PIDController(EngagementConstants.kStationFastP, EngagementConstants.kStationI, EngagementConstants.kStationD);
        engagePID.enableContinuousInput(-180, 180);
        engagePID.setTolerance(2);

        //initializes subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        //initializes widgets
        if(pitchAngleWidget == null) {
            pitchAngleWidget = engagementTab.add("Pitch Angle", 0).withSize(2, 1).getEntry();
            engagementTab.add("Target Angle", EngagementConstants.kChargingStationSlack).withSize(2, 1).getEntry();
            errorAngleWidget =  engagementTab.add("Error", 0).withSize(2, 1).getEntry();  
            powerWidget = engagementTab.add("Power", 0).withSize(2, 1).getEntry();  
        }
    }

    @Override
    public void initialize() {  
        iteration = 0; //acts as a timer
        initialAngle = m_driveSubsystem.getPitch() + RobotConstants.kPitchOffset;
        System.out.println("Initial Angle: "+initialAngle);
    }

    @Override
    public void execute() {
        //gets the current angle and uses it to calculate an adjusted speed using the PID controller
        double currentAngle = m_driveSubsystem.getPitch() + RobotConstants.kPitchOffset;
        double percentPower = engagePID.calculate(currentAngle, 0);

        if(Math.abs(currentAngle) < Math.abs(initialAngle) - EngagementConstants.kAngleChange && engagePID.getP() == EngagementConstants.kStationFastP) {
            engagePID.setP(EngagementConstants.kStationSlowP);
            System.out.println("God's plan: "+engagePID.getP());
        }
        
        //limits the motor's power to a max of 0.4
        if(Math.abs(percentPower) > 0.45) {
            percentPower = Math.copySign(0.45, percentPower);
        }

        //setting the power of the motors
        m_driveSubsystem.drive(percentPower, percentPower);

        //if the robot is considered in the "balanceing" range, it starts the timer
        //resets the timer if the if statement condition is not met
        if(Math.abs(currentAngle) < EngagementConstants.kChargingStationSlack) {
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
        engagePID.setP(EngagementConstants.kStationFastP);
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //finishes the method if the robot is balanced for 1 second
        return iteration >= EngagementConstants.kBalanceTimer;
    }
}
