package frc.robot.commands.Arm;

//subsystems and commands
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

//constructor
public class IntakePositionCommand extends ParallelCommandGroup {
    public IntakePositionCommand(BicepArmSubsystem bicepArmSubsystem, ElbowPivotSubsystem elbowPivotSubsystem) {
        addCommands(new RetractBicepCommand(bicepArmSubsystem));
        addCommands(new AutoPivotElbowCommand(0, elbowPivotSubsystem));
    }
}
