package frc.robot.commands.Claw;

//subsystems and commands
import frc.robot.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ToggleClawCommand extends CommandBase {
    //declaring subsystems
    private ClawSubsystem m_ClawSubsystem;

    //declaring the initial position of the claw's solenoid
    private DoubleSolenoid.Value intialClawState;

    //constructor
    public ToggleClawCommand(ClawSubsystem clawSubsystem) {
        //initializing subsystems
        m_ClawSubsystem = clawSubsystem;
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {
        intialClawState = m_ClawSubsystem.getClawState();
        m_ClawSubsystem.toggleClaw();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Claw state toggled");
    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() != intialClawState;
    }
}
