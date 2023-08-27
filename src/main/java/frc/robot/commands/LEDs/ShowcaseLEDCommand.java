package frc.robot.commands.LEDs;

//subsystems and commands
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.AnimationTypes;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports


public class ShowcaseLEDCommand extends CommandBase {
    //declaring subsystems
    private LEDSubsystem m_LEDSubsystem;

    //constructor
    public ShowcaseLEDCommand(LEDSubsystem ledSubsystem) {
        //initializing subsystems
        m_LEDSubsystem = ledSubsystem;
        addRequirements(m_LEDSubsystem);
    }

    @Override
    public void initialize() {
        m_LEDSubsystem.setHSV(0, 0, 0);
        m_LEDSubsystem.setLEDAnimation(AnimationTypes.SetAll);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("showcase LED display");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
