package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AlignmentCommand;
import frc.robot.commands.LimelightTravelDistanceCommand;

public class ArriveToGrid extends SequentialCommandGroup{

    private int pipeline;
    private double targetHeight;

    public ArriveToGrid(int pipeline, double targetHeight) {
        this.pipeline = pipeline;
        this.targetHeight = targetHeight;

        addCommands(new AlignmentCommand(pipeline));
        addCommands(new LimelightTravelDistanceCommand(targetHeight));
    }
}
