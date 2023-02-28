package frc.robot.commands.Autonomous;

import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.GyroEngageCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//constants
import frc.robot.Constants.GameConstants;

public class GyroEngageAuto extends SequentialCommandGroup {
    private double distance;

    public GyroEngageAuto(double distance) {
        this.distance = distance;
        //moves onto the charging station
        addCommands(new TravelDistanceCommand(distance, 0.5));
        
        // //balances on the charging station
        // addCommands(new GyroEngageCommand());
    }
}
