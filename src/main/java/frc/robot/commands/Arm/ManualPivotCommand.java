package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.ArmConstants;

public class ManualPivotCommand extends CommandBase {
    //declaring subsystems
    private ElbowPivotSubsystem m_elbowPivotSubsystem;

    //declaring the joystick used
    private GenericHID joystick;

    //constructor
    public ManualPivotCommand(GenericHID joystick, ElbowPivotSubsystem elbowPivotSubsystem) {
        this.joystick = joystick;
        m_elbowPivotSubsystem = elbowPivotSubsystem;

        addRequirements(m_elbowPivotSubsystem);
    }

    private boolean atMaxLimit() {
        return (
            m_elbowPivotSubsystem.getLeftElbowEncoder() >= ArmConstants.kMaxPivot && 
            m_elbowPivotSubsystem.getRightElbowEncoder() >= ArmConstants.kMaxPivot
        );
    }

    private boolean atMinLimit() {
        return (
            m_elbowPivotSubsystem.getLeftElbowEncoder() <= ArmConstants.kMinimumPivot &&
            m_elbowPivotSubsystem.getRightElbowEncoder() <= ArmConstants.kMinimumPivot
        );
    }
    
    @Override
    public void execute() {
        double speed = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * 0.3;
    
        if((atMaxLimit() && speed > 0) || (atMinLimit() && speed <0)) {
            speed = 0;
        }
     
        m_elbowPivotSubsystem.setPivotSpeed(speed);

        m_elbowPivotSubsystem.printEncoders(m_elbowPivotSubsystem.getLeftElbowEncoder(), m_elbowPivotSubsystem.getRightElbowEncoder());
    }

    @Override
    public void end(boolean interrupted) {
        m_elbowPivotSubsystem.setStallSpeed();
    }

    //in progress
    @Override
    public boolean isFinished() {
        return Math.abs(joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort)) < 0.2;
    }
}

