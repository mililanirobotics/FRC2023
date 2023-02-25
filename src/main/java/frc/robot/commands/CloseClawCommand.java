package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClawSubsystem;

public class CloseClawCommand extends CommandBase {
    private ClawSubsystem m_ClawSubsystem;

    public void CloseClawCommand() {
        m_ClawSubsystem = RobotContainer.clawSubsystem;
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_ClawSubsystem.leftClaw.set(Value.kForward);
        m_ClawSubsystem.rightClaw.set(Value.kForward);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() == Value.kForward;
    }
}
