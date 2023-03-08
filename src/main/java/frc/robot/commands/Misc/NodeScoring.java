package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Arm.AutoPivotElbowCommand;
import frc.robot.commands.Arm.ExtendBicepCommand;
import frc.robot.commands.Claw.OpenClawCommand;
import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;

public class NodeScoring extends SequentialCommandGroup{

    private int pipeline;
    private double angleRotation;

    public NodeScoring(double angleRotation, BicepArmSubsystem bicepArmSubsystem, ClawSubsystem clawSubsystem, ElbowPivotSubsystem elbowPivotSubsystem, DriveSubsystem driveSubsystem) {
        this.angleRotation = angleRotation;

        addCommands(new ExtendBicepCommand(bicepArmSubsystem));
        addCommands(new AutoPivotElbowCommand(angleRotation, elbowPivotSubsystem));
        addCommands(new OpenClawCommand(clawSubsystem));
        addCommands(new TravelDistanceCommand(26, 0.35, driveSubsystem));
        addCommands(new AutoPivotElbowCommand(0, elbowPivotSubsystem));
    }
}