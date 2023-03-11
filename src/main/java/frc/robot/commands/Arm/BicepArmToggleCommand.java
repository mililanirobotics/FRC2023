package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.BicepArmSubsystem; 
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BicepArmToggleCommand extends CommandBase {
    //declaring subsystems
    private BicepArmSubsystem m_bicepArmSubsystem;

    //declaring the initial position of the bicep's solenoid
    private DoubleSolenoid.Value initialBicepState;

    //constructor
    public BicepArmToggleCommand(BicepArmSubsystem bicepArmSubsystem) {
        //initializing subsystems
        m_bicepArmSubsystem = bicepArmSubsystem;
        addRequirements(m_bicepArmSubsystem);
    }

    @Override
    public void initialize() {
        initialBicepState = m_bicepArmSubsystem.bicepState();
        m_bicepArmSubsystem.toggleBicep();
        System.out.println("Initial position: "+initialBicepState);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Position toggled");
    }

    public boolean isFinished() {
        return initialBicepState != m_bicepArmSubsystem.bicepState();
    }
}