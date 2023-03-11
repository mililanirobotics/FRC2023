package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotContainer;
//constants
import frc.robot.Constants.RobotConstants;

public class AutoPivotElbowCommand extends CommandBase {
    //declaring subsystems
    private ElbowPivotSubsystem m_elbowPivotSubsystem;
    //declaring PID controller and the angle that will act as the setpoint
    private PIDController pivotPID;
    private double angleInCounts;

    //constructor
    public AutoPivotElbowCommand(double angleRotation, ElbowPivotSubsystem elbowPivotSubsystem) {
        //initializing subsystems, PID controller, and angle of rotation
        m_elbowPivotSubsystem = elbowPivotSubsystem;
        angleInCounts = m_elbowPivotSubsystem.convertAngle(angleRotation);
        pivotPID = new PIDController(RobotConstants.kPivotP, RobotConstants.kPivotI, RobotConstants.kPivotD);
        
        addRequirements(m_elbowPivotSubsystem);
    }

    @Override
    public void execute() {
        //calculating speed based on the error from the target encoder value 
        double speed = pivotPID.calculate(m_elbowPivotSubsystem.getRightElbowEncoder(), angleInCounts);
        speed = RobotContainer.limitSpeed(speed, 0.35, 0.5);

        //setting the elbow pivot speed 
        m_elbowPivotSubsystem.setPivotSpeed(speed);

        //debugging statements (in counts)
        System.out.println("Error (in counts): " + (angleInCounts - m_elbowPivotSubsystem.getRightElbowEncoder()));
        System.out.println("Angle (in counts): " + angleInCounts);
        System.out.println("Speed: " + speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_elbowPivotSubsystem.shutdown();
    }

    @Override
    public boolean isFinished() {
        return m_elbowPivotSubsystem.getRightElbowEncoder() >= angleInCounts;
    }
}
