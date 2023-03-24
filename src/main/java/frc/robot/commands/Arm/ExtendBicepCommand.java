package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.BicepArmSubsystem; 
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ExtendBicepCommand extends CommandBase {
    //declaring subsystems
    private BicepArmSubsystem m_BicepArmSubsystem;
    //timer
    private int iteration;

    //constructor
    public ExtendBicepCommand(BicepArmSubsystem bicepArmSubsystem) {
        m_BicepArmSubsystem = bicepArmSubsystem;
    }

    @Override
    public void initialize() {
        m_BicepArmSubsystem.extendBicep();
        m_BicepArmSubsystem.updateState();
        
        iteration = 0;
    }

    @Override
    public void execute() {
        iteration++;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Bicep extended outside the frame");
    }

    @Override
    public boolean isFinished() {
        return m_BicepArmSubsystem.bicepState() == Value.kForward && iteration > 50;
    }
}
