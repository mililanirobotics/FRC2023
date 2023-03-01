package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// Ground position may be at a different angle. 
// Encoder movements for Elbow pivot is subject to change without resetting encoders.
public class PayloadIntake extends SequentialCommandGroup {
    
    public PayloadIntake() {
        addCommands(new ExtendBicepCommand());
        addCommands(new AutoPivotElbowCommand(0));
    }
}
