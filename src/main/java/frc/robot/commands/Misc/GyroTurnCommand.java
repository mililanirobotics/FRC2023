package frc.robot.commands.Misc;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.RobotConstants;

public class GyroTurnCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring measured values
    private double turnDegrees;
    private double currentYaw;

    //declaring PID controller
    private PIDController turnDrivePID;

    //declaring widgets
    private GenericEntry powerWidget;
    private GenericEntry yawWidget;

    public GyroTurnCommand(DriveSubsystem driveSubsystem, double turnDegrees, ShuffleboardTab motorTab) {
        //initializing measured values
        this.turnDegrees = turnDegrees;

        //initializing PID controller
        turnDrivePID = new PIDController(RobotConstants.kTurnDriveP, RobotConstants.kTurnDriveI, RobotConstants.kTurnDriveD);

        //initializing subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        //initializing widgets
        powerWidget = motorTab.add("Power", 0).withSize(2, 1).getEntry();
        yawWidget = motorTab.add("Yaw", 0).withSize(2, 1).getEntry();
        motorTab.add("Target Yaw", turnDegrees).withSize(2, 1).getEntry();
    }

    @Override
    public void initialize() {
        //resets the gyro 
        m_driveSubsystem.zeroOutGyro();
    }

    @Override
    public void execute() {
        //gets the current yaw and uses it to calculate an adjusted speed using the PID controller
        currentYaw = m_driveSubsystem.getYaw();
        double percentPower = turnDrivePID.calculate(currentYaw, turnDegrees);

        //limits the speed of the motors
        percentPower = RobotContainer.limitSpeed(percentPower, 0.35, 0.6);
    
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
