package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NodeScoring extends SequentialCommandGroup{

    private int pipeline;
    private double angleRotation;

    public NodeScoring(double angleRotation) {
        this.angleRotation = angleRotation;

        addCommands(new ExtendBicepCommand());
        addCommands(new AutoPivotElbowCommand(angleRotation));
        addCommands(new OpenClawCommand());
        addCommands(new TravelDistanceCommand(26, 0.35));
        addCommands(new AutoPivotElbowCommand(0));
    }
}