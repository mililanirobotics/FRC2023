package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Constants.JoystickConstants;

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

    @Override
    public void execute() {
        double speed = -joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort);
        m_elbowPivotSubsystem.setPivotSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_elbowPivotSubsystem.shutdown();
    }

    //in progress
    @Override
    public boolean isFinished() {
        return Math.abs(joystick.getRawAxis(JoystickConstants.kLeftYJoystickPort)) < 0.2;
    }
}

