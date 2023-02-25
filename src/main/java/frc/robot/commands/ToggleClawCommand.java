package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClawConstants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ClawSubsystem;

public class ToggleClawCommand extends CommandBase {
    private ClawSubsystem m_ClawSubsystem;
    private DoubleSolenoid.Value intialClawState;

    public void ToggleClawCommand() {
        m_ClawSubsystem = RobotContainer.clawSubsystem;
        // intialClawState = m_ClawSubsystem.getClawState();
        addRequirements(m_ClawSubsystem);
    }

    @Override
    public void initialize() {
        intialClawState = m_ClawSubsystem.getClawState();
    }

    @Override
    public void execute() {
        System.out.println("toggle");
        m_ClawSubsystem.leftClaw.toggle();
        m_ClawSubsystem.rightClaw.toggle();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_ClawSubsystem.getClawState() != intialClawState;
    }
}
