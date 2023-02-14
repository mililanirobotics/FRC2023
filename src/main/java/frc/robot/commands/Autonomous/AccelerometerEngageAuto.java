package frc.robot.commands.Autonomous;

import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.AccelerometerEngageCommand;
//constants
import frc.robot.Constants.GameConstants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AccelerometerEngageAuto extends SequentialCommandGroup {
    
    public AccelerometerEngageAuto() {
        //moves onto the charging station
        addCommands(new TravelDistanceCommand(GameConstants.kChargingStationDistance, 0.5));

        //balances on the charging station using the built in 3-axis accelerometer
        addCommands(new AccelerometerEngageCommand());
    }
}
