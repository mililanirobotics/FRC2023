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

public class PivotDegreesCommand extends CommandBase {
    //declaring subsystems
    private ElbowPivotSubsystem m_elbowPivotSubsystem;
    //declaring PID controller and the angle that will act as the setpoint
    private double angleInCounts;
    private double initialLeftCounts;
    private double initialRightCounts;

    //constructor
    public PivotDegreesCommand(double angleRotation, ElbowPivotSubsystem elbowPivotSubsystem, ShuffleboardTab armTab) {
        //initializing subsystems, PID controller, and angle of rotation
        m_elbowPivotSubsystem = elbowPivotSubsystem;

        angleInCounts = m_elbowPivotSubsystem.convertAngle(angleRotation);
        
        addRequirements(m_elbowPivotSubsystem);
    }

    @Override
    public void initialize() {
        initialLeftCounts = m_elbowPivotSubsystem.getLeftElbowEncoder();
        initialRightCounts = m_elbowPivotSubsystem.getRightElbowEncoder();

        m_elbowPivotSubsystem.setPivotSpeed(Math.copySign(0.1, initialLeftCounts));
    }

    @Override
    public void execute() {
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
        return Math.abs(m_elbowPivotSubsystem.getLeftElbowEncoder - (initialLeftCounts + angleInCounts) < 5) &&
            Math.abs(m_elbowPivotSubsystem.getRightElbowEncoder - (initialLeftCounts + angleInCounts) < 5);
    }
}
