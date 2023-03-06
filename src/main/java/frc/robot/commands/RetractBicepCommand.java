package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BicepArmSubsystem; 

public class RetractBicepCommand extends CommandBase{
    BicepArmSubsystem m_BicepArmSubsystem;

    public RetractBicepCommand(BicepArmSubsystem bicepArmSubsystem) {
        m_BicepArmSubsystem = bicepArmSubsystem;
    }

    @Override
    public void initialize() {
        m_BicepArmSubsystem.retractBicep();
    }

    @Override
    public void execute() {
        System.out.println("Reverse");
    }

    @Override
    public void end(boolean interrupted) {

    }
    
    @Override 
    public boolean isFinished() {
        return m_BicepArmSubsystem.bicepState() == Value.kReverse;
    }
}
