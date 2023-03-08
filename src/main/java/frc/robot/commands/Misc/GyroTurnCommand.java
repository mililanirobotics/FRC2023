package frc.robot.commands.Misc;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;

//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;

//subsystems used
import frc.robot.subsystems.DriveSubsystem;


public class GyroTurnCommand extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private double turnDegrees;
    private double currentYaw;

    private GenericEntry powerWidget;
    private GenericEntry yawWidget;
    private PIDController turnDrivePID;

    public GyroTurnCommand(DriveSubsystem driveSubsystem, double turnDegrees, ShuffleboardTab motorTab) {
        this.turnDegrees = turnDegrees;
        turnDrivePID = new PIDController(RobotConstants.kTurnDriveP, RobotConstants.kTurnDriveI, RobotConstants.kTurnDriveD);
        powerWidget = motorTab.add("Power", 0).withSize(2, 1).getEntry();
        yawWidget = motorTab.add("Yaw", 0).withSize(2, 1).getEntry();
        motorTab.add("Target Yaw", turnDegrees).withSize(2, 1).getEntry();

        turnDrivePID.getVelocityError();
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.zeroOutGyro();
    }

    @Override
    public void execute() {
        currentYaw = m_driveSubsystem.getYaw();
        double percentPower = turnDrivePID.calculate(currentYaw, turnDegrees);

        //retains the sign of the original power double
        //assigns the max/min power if it goes over or under
        if(Math.abs(percentPower) > 0.6) {
            percentPower = Math.copySign(0.6, percentPower);
        } 
        else if(Math.abs(percentPower) < 0.35) {
            percentPower = Math.copySign(0.35, percentPower);
        }
    
        //if error is +, left speed + and right speed - (turns clockwise)
        //if error is -, left speed - and right speed + (turns counterclockwise)
        m_driveSubsystem.drive(percentPower, -percentPower);

        //debuggin statements
        powerWidget.setDouble(percentPower);
        yawWidget.setDouble(currentYaw);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Turn command ended");
        m_driveSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        //ends the command if the error is within the desired range
        return Math.abs(turnDegrees - currentYaw) < GameConstants.kTurnSlack;
    }

}
