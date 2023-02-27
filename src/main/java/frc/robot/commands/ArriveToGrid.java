package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

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
