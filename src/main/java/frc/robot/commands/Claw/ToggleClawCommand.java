package frc.robot.commands.Claw;

import frc.robot.subsystems.BicepArmSubsystem;
//subsystems and commands
import frc.robot.subsystems.ClawSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ToggleClawCommand extends CommandBase {
    //declaring subsystems
    private ClawSubsystem m_clawSubsystem;
    private BicepArmSubsystem m_bicepSubsystem;

    //declaring the initial position of the claw's solenoid
    private DoubleSolenoid.Value intialClawState;

    //constructor
    public ToggleClawCommand(ClawSubsystem clawSubsystem, BicepArmSubsystem bicepSubsystem) {
        //initializing subsystems
        m_bicepSubsystem = bicepSubsystem;
        m_clawSubsystem = clawSubsystem;
        addRequirements(m_clawSubsystem);
    }

    @Override
    public void initialize() {
        intialClawState = m_clawSubsystem.getClawState();
        m_clawSubsystem.toggleClaw();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Bicep State: "+m_bicepSubsystem.bicepState());
        System.out.println("Claw state toggled");
    }

    @Override
    public boolean isFinished() {
        return m_clawSubsystem.getClawState() != intialClawState;
    }
}
