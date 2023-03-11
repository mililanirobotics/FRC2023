package frc.robot.commands.Misc;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import frc.robot.commands.Arm.AutoPivotElbowCommand;
import frc.robot.commands.Arm.ExtendBicepCommand;
import frc.robot.commands.Claw.OpenClawCommand;

//constructor
public class NodeScoringCommand extends SequentialCommandGroup {
    public NodeScoringCommand(double angleRotation, BicepArmSubsystem bicepArmSubsystem, ClawSubsystem clawSubsystem, ElbowPivotSubsystem elbowPivotSubsystem) {
        addCommands(new ExtendBicepCommand(bicepArmSubsystem));
        addCommands(new AutoPivotElbowCommand(angleRotation, elbowPivotSubsystem));
        addCommands(new OpenClawCommand(clawSubsystem));
        addCommands(new AutoPivotElbowCommand(0, elbowPivotSubsystem));
    }
}