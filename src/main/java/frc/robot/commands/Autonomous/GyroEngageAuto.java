package frc.robot.commands.Autonomous;

import frc.robot.commands.TravelDistanceCommand;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GyroEngageAuto extends SequentialCommandGroup {

    public GyroEngageAuto(DriveSubsystem driveSubsystem, double distance, ShuffleboardTab motorTab) {
        //moves onto the charging station
        addCommands(new TravelDistanceCommand(distance, 0.5, driveSubsystem));
        
        // //balances on the charging station
        // addCommands(new GyroEngageCommand());
    }
}
