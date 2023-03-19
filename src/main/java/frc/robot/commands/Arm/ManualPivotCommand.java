package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.PivotConstants;

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
            m_elbowPivotSubsystem.getLeftElbowEncoder() >= PivotConstants.kMaxPivot && 
            m_elbowPivotSubsystem.getRightElbowEncoder() >= PivotConstants.kMaxPivot
        );
    }

    private boolean atMinLimit() {
        return (
            m_elbowPivotSubsystem.getLeftElbowEncoder() <= PivotConstants.kMinimumPivot &&
            m_elbowPivotSubsystem.getRightElbowEncoder() <= PivotConstants.kMinimumPivot
        );
    }
    
    @Override
    public void execute() {
        double speed = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort) * 0.3;
    
        // if((atMaxLimit() && speed > 0) || (atMinLimit() && speed <0)) {
        //     speed = 0;
        // }
     
        m_elbowPivotSubsystem.setPivotSpeed(speed);

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
    }

    //in progress
    @Override
    public boolean isFinished() {
        return Math.abs(joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort)) < 0.2;
    }
}

