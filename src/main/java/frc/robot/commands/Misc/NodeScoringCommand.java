package frc.robot.commands.Misc;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import frc.robot.commands.Arm.AutoPivotElbowCommand;
import frc.robot.commands.Arm.ExtendBicepCommand;
import frc.robot.commands.Claw.OpenClawCommand;
//generic imports
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

//constructor
public class NodeScoringCommand extends SequentialCommandGroup {
    public NodeScoringCommand(double angleRotation, BicepArmSubsystem bicepArmSubsystem, ClawSubsystem clawSubsystem, ElbowPivotSubsystem elbowPivotSubsystem, ShuffleboardTab armTab) {
        addCommands(new ExtendBicepCommand(bicepArmSubsystem));
        addCommands(new AutoPivotElbowCommand(angleRotation, elbowPivotSubsystem, armTab));
        addCommands(new OpenClawCommand(clawSubsystem));
        addCommands(new AutoPivotElbowCommand(0, elbowPivotSubsystem, armTab));
    }
}