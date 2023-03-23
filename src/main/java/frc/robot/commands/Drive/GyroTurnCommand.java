package frc.robot.commands.Drive;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.DriveConstants;

public class GyroTurnCommand extends CommandBase {
    //declaring subsystems
    private DriveSubsystem m_driveSubsystem;

    //declaring measured values
    private double turnDegrees;
    private double currentYaw;

    //declaring PID controller
    private PIDController turnDrivePID;

    //declaring widgets
    private static GenericEntry powerWidget;
    private static GenericEntry yawWidget;

    public GyroTurnCommand(DriveSubsystem driveSubsystem, double turnDegrees, ShuffleboardTab turnTab) {
        //initializing measured values
        this.turnDegrees = turnDegrees;

        //initializing PID controller
        turnDrivePID = new PIDController(DriveConstants.kTurnDriveP, DriveConstants.kTurnDriveI, DriveConstants.kTurnDriveD);
        turnDrivePID.enableContinuousInput(-180, 180);

        //initializing subsystems
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);

        if(yawWidget == null) {
            //initializing widgets
            powerWidget = turnTab.add("Power", 0).withSize(2, 1).getEntry();
            yawWidget = turnTab.add("Yaw", 0).withSize(2, 1).getEntry();
            turnTab.add("Target Yaw", turnDegrees).withSize(2, 1).getEntry();
        }
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
        percentPower = RobotContainer.limitSpeed(percentPower, 0.2, 0.5);
    
        //if error is +, left speed + and right speed - (turns clockwise)
        //if error is -, left speed - and right speed + (turns counterclockwise)
        m_driveSubsystem.drive(percentPower, -percentPower);

        //debuggin statements
        System.out.println("Running");
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
        return Math.abs(turnDegrees - currentYaw) < DriveConstants.kTurnSlack;
    }

}
