package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClawSubsystem;

public class CloseClawCommand extends CommandBase {
    private ClawSubsystem m_ClawSubsystem;

    public CloseClawCommand() {
        m_ClawSubsystem = RobotContainer.clawSubsystem;
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {
        m_ClawSubsystem.leftClaw.set(Value.kReverse);
        m_ClawSubsystem.rightClaw.set(Value.kReverse);
    }

    @Override
    public void execute() {
        System.out.println("close");
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() == Value.kForward;
    }
}
