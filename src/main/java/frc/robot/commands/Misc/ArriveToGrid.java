package frc.robot.commands.Misc;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Alignment.AlignmentCommand;
import frc.robot.commands.Drive.LimelightTravelDistanceCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class ArriveToGrid extends SequentialCommandGroup{

    private LimelightSubsystem.Pipeline pipeline;
    private double targetHeight;

    public ArriveToGrid(LimelightSubsystem.Pipeline pipeline, double targetHeight, LimelightSubsystem limelightSubsystem, DriveSubsystem driveSubsystem) {
        this.pipeline = pipeline;
        this.targetHeight = targetHeight;

        addCommands(new AlignmentCommand(pipeline, limelightSubsystem));
        addCommands(new LimelightTravelDistanceCommand(targetHeight, limelightSubsystem, driveSubsystem));
    }
}
