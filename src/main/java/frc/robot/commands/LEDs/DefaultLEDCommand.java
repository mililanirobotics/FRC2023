package frc.robot.commands.LEDs;

//subsystems and commands
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LEDSubsystem.AnimationTypes;
import edu.wpi.first.wpilibj2.command.CommandBase;
//general imports


public class DefaultLEDCommand extends CommandBase {
    //declaring subsystems
    private LEDSubsystem m_LEDSubsystem;

    //constructor
    public DefaultLEDCommand(LEDSubsystem ledSubsystem) {
        //initializing subsystems
        m_LEDSubsystem = ledSubsystem;
        addRequirements(m_LEDSubsystem);
    }

    @Override
    public void initialize() {
        m_LEDSubsystem.setHSV(0, 0, 0);
        m_LEDSubsystem.setLEDAnimation(AnimationTypes.SetAll);
        m_LEDSubsystem.toggleSignalLED();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("default LED displaying");
    }

    @Override
    public boolean isFinished() {
        return m_LEDSubsystem.getSignalLED() == false;
    }
}
