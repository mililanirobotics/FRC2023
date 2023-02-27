package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BicepArmSubsystem;

import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.RobotConstants; 

public class BicepArmToggleCommand extends CommandBase{
    private BicepArmSubsystem m_BicepArmSubsystem;
    private DoubleSolenoid.Value initialBicepState;

    public BicepArmToggleCommand() {
        m_BicepArmSubsystem = RobotContainer.bicepArmSubsystem;

        addRequirements(m_BicepArmSubsystem);
    }

    public void initialize() {
        initialBicepState = m_BicepArmSubsystem.bicepState();
        m_BicepArmSubsystem.bicepArm.toggle();

    }

    public void execute() {

    }

    public void end(boolean interrupted) {

    }

    public boolean isFinished() {
        return initialBicepState != m_BicepArmSubsystem.bicepState();
    }
}