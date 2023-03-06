package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BicepArmSubsystem;

// Retracting Payload into Robot may be redundant and is prone to deletion if so.
public class DrivePayloadPosition extends SequentialCommandGroup{
    
    public DrivePayloadPosition(BicepArmSubsystem bicepArmSubsystem) {
        addCommands(new RetractBicepCommand(bicepArmSubsystem));
    }
}
