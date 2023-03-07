package frc.robot.commands.Autonomous;

import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GyroEngageAuto extends SequentialCommandGroup {

    public GyroEngageAuto(DriveSubsystem driveSubsystem, double distance, ShuffleboardTab motorTab) {
        //moves onto the charging station
        addCommands(new TravelDistanceCommand(driveSubsystem, distance, 0.5, motorTab));
        
        // //balances on the charging station
        // addCommands(new GyroEngageCommand());
    }
}
