package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BicepArmSubsystem; 

public class BicepArmToggleCommand extends CommandBase{
    private BicepArmSubsystem m_BicepArmSubsystem;
    private DoubleSolenoid.Value initialBicepState;

    public BicepArmToggleCommand() {
        m_BicepArmSubsystem = RobotContainer.bicepArmSubsystem;

        addRequirements(m_BicepArmSubsystem);
    }

    public void initialize() {
        initialBicepState = m_BicepArmSubsystem.bicepState();
        m_BicepArmSubsystem.toggleBicep();

    }

    public void execute() {

    }

    public void end(boolean interrupted) {

    }

    public boolean isFinished() {
        return initialBicepState != m_BicepArmSubsystem.bicepState();
    }
}