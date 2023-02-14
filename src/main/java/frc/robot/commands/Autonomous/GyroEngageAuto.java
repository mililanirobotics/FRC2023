package frc.robot.commands.Autonomous;

import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.GyroEngageCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//constants
import frc.robot.Constants.GameConstants;

public class GyroEngageAuto extends SequentialCommandGroup {

    public GyroEngageAuto() {
        //moves onto the charging station
        addCommands(new TravelDistanceCommand(GameConstants.kChargingStationDistance, 0.2));
        
        //balances on the charging station
        addCommands(new GyroEngageCommand());
    }
}
