package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClawSubsystem;

public class OpenClawCommand extends CommandBase {
    private ClawSubsystem m_ClawSubsystem;

    public OpenClawCommand() {
        m_ClawSubsystem = RobotContainer.clawSubsystem;
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {
        m_ClawSubsystem.openClaw();
    }

    @Override
    public void execute() {
        System.out.println("open");
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() == Value.kForward;
    }
}
