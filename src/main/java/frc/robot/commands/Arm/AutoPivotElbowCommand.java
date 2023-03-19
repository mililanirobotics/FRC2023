package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.PivotConstants;
//constants
import frc.robot.Constants.RobotConstants;

public class AutoPivotElbowCommand extends CommandBase {
    //declaring subsystems
    private ElbowPivotSubsystem m_elbowPivotSubsystem;
    //declaring PID controller and the angle that will act as the setpoint
    private PIDController pivotPID;
    private double angleInCounts;
    //declaring widgets
    private static GenericEntry errorWidget;
    private static GenericEntry powerWidget;
    private static GenericEntry targetWidget;

    //constructor
    public AutoPivotElbowCommand(double angleRotation, ElbowPivotSubsystem elbowPivotSubsystem, ShuffleboardTab armTab) {
        //initializing subsystems, PID controller, and angle of rotation
        m_elbowPivotSubsystem = elbowPivotSubsystem;
        angleInCounts = m_elbowPivotSubsystem.convertAngle(angleRotation);
        pivotPID = new PIDController(RobotConstants.kPivotP, RobotConstants.kPivotI, RobotConstants.kPivotD);
        
        addRequirements(m_elbowPivotSubsystem);

        if(errorWidget == null) {
            //initializing widets
            errorWidget = armTab.add("Error in counts", 0).withSize(2, 1).getEntry();
            powerWidget = armTab.add("Elbow Motor Power", 0).withSize(2, 1).getEntry();
            targetWidget = armTab.add("Target angle in counts", 0).withSize(2, 1).getEntry();
        }
    }

    @Override
    public void initialize() {
        targetWidget.setDouble(angleInCounts);
    }

    @Override
    public void execute() {
        //calculating speed based on the error from the target encoder value 
        double speed = pivotPID.calculate(m_elbowPivotSubsystem.getLeftElbowEncoder(), angleInCounts);
        speed = RobotContainer.limitSpeed(speed, 0.3, 0.5);

        //setting the elbow pivot speed 
        m_elbowPivotSubsystem.setPivotSpeed(speed);

        //debugging statements (in counts)
        errorWidget.setDouble(angleInCounts - m_elbowPivotSubsystem.getLeftElbowEncoder());
        powerWidget.setDouble(speed);

        m_elbowPivotSubsystem.printEncoders(m_elbowPivotSubsystem.getLeftElbowEncoder(), m_elbowPivotSubsystem.getRightElbowEncoder());
    }

    @Override
    public void end(boolean interrupted) {
        if(m_elbowPivotSubsystem.isAtStallPosition()) {
            m_elbowPivotSubsystem.setPivotSpeed(PivotConstants.kStallSpeed);
        }
        else {
            m_elbowPivotSubsystem.shutdown();
        }
        System.out.println(angleInCounts);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angleInCounts - m_elbowPivotSubsystem.getLeftElbowEncoder()) < m_elbowPivotSubsystem.convertAngle(GameConstants.kAutoPivotSlack) &&
            Math.abs(angleInCounts - m_elbowPivotSubsystem.getRightElbowEncoder()) < m_elbowPivotSubsystem.convertAngle(GameConstants.kAutoPivotSlack);
    }
}
