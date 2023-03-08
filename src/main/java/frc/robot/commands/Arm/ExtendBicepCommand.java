package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.subsystems.BicepArmSubsystem; 

public class ExtendBicepCommand extends CommandBase{
    BicepArmSubsystem m_BicepArmSubsystem;

    public ExtendBicepCommand(BicepArmSubsystem bicepArmSubsystem) {
        m_BicepArmSubsystem = bicepArmSubsystem;
    }

    public void initialize() {
        m_BicepArmSubsystem.extendBicep();
    }

    public void execute() {

    }

    public void end(boolean interrupted) {
        
    }

    public boolean isFinished() {
        return m_BicepArmSubsystem.bicepState() == Value.kForward;
    }
}
