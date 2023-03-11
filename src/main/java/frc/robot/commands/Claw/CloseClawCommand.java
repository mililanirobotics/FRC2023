package frc.robot.commands.Claw;

//subsystems and commands
import frc.robot.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class CloseClawCommand extends CommandBase {
    //declaring subsystems
    private ClawSubsystem m_ClawSubsystem;

    //constructor
    public CloseClawCommand(ClawSubsystem clawSubsystem) {
        //initializing subsystems
        m_ClawSubsystem = clawSubsystem;
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {
        m_ClawSubsystem.closeClaw();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Claw closed");
    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() == Value.kForward;
    }
}
