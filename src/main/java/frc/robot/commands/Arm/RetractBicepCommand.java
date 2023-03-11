package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.BicepArmSubsystem; 
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class RetractBicepCommand extends CommandBase {
    //declaring subsystems
    private BicepArmSubsystem m_BicepArmSubsystem;

    //constructor
    public RetractBicepCommand(BicepArmSubsystem bicepArmSubsystem) {
        m_BicepArmSubsystem = bicepArmSubsystem;
    }

    @Override
    public void initialize() {
        m_BicepArmSubsystem.retractBicep();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Bicep extended outside the frame");
    }
    
    @Override 
    public boolean isFinished() {
        return m_BicepArmSubsystem.bicepState() == Value.kReverse;
    }
}
