package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BicepArmSubsystem;

import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.RobotConstants; 

public class ExtendBicepCommand extends CommandBase{
    BicepArmSubsystem m_BicepArmSubsystem;

    public ExtendBicepCommand() {
        m_BicepArmSubsystem = RobotContainer.bicepArmSubsystem;
    }

    public void initialize() {
        m_BicepArmSubsystem.bicepArm.set(Value.kForward);
    }

    public void execute() {

    }

    public void end(boolean interrupted) {
        
    }

    public boolean isFinished() {
        return m_BicepArmSubsystem.bicepState() == Value.kForward;
    }
}
