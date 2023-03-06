package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;

// Ground position may be at a different angle. 
// Encoder movements for Elbow pivot is subject to change without resetting encoders.
public class PayloadIntake extends SequentialCommandGroup {
    
    public PayloadIntake(BicepArmSubsystem bicepArmSubsystem, ElbowPivotSubsystem elbowPivotSubsystem) {
        addCommands(new ExtendBicepCommand(bicepArmSubsystem));
        addCommands(new AutoPivotElbowCommand(0, elbowPivotSubsystem));
    }
}
