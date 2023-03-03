package frc.robot.commands;

import org.w3c.dom.Node;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutonomousConstants;

public class Autonomous extends SequentialCommandGroup{
    
    private String autoPath;

    public Autonomous(String autoPath) {
        this.autoPath = autoPath;

        switch(autoPath) {
            case AutonomousConstants.kDockAndEngage1: //scoring preload then balance and engage in left position
                addCommands(new NodeScoring(0)); //set arm to score
                addCommands(new TravelDistanceCommand(-198, 0.5)); //drive backwards 16.5 ft for mobility
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                addCommands(new GyroTurnCommand(-90)); //turn robot left 90 degrees (relative to starting position)
                addCommands(new TravelDistanceCommand(60, 0.3)); //drives foward 5ft
                addCommands(new GyroTurnCommand(90)); //turn robot right 90 degrees
                addCommands(new TravelDistanceCommand(36, 0.3));//drives foward 3 ft
                //start of balancing method 
                break;

            case AutonomousConstants.kDockAndEngage2: //scoring preload then balance and engage in mid position
                addCommands(new NodeScoring(0)); //set arm to score
                addCommands(new TravelDistanceCommand(-200, 0.5)); //drive backwards 16.5 ft (+2 inches to account for going over charge station)
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                // addCommands(new GyroTurnCommand(180));
                addCommands(new TravelDistanceCommand(36, 0.4)); //drive foward heading to balance on charge station
                //start of balancing method
                break;

            case AutonomousConstants.kDockAndEngage3: //scoring preload then balance and engage in right position
                addCommands(new NodeScoring(0)); //set arm to score
                addCommands(new TravelDistanceCommand(-200, 0.5)); //drive backwards 16.5 ft for mobility
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                addCommands(new GyroTurnCommand(90)); //turns 90 degree right (relative to robot starting position)
                addCommands(new TravelDistanceCommand(60, 0.3)); //drives foward 5ft
                addCommands(new GyroTurnCommand(-90)); //turn 90 degree left to face charge station
                addCommands(new TravelDistanceCommand(36, 0.3)); //drive foward heading to balance on charge station
                //start of balancing method
                break;
            
            case AutonomousConstants.kScoring1: //scoring only in left position
                addCommands(new NodeScoring(0));//set arm to score
                addCommands(new TravelDistanceCommand(-200, 0.5)); //drive backwards 16.5 ft 
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                addCommands(new GyroTurnCommand(180)); //turns robot 180 degrees right relative to starting position (+5 for game piece heading)
                addCommands(new TravelDistanceCommand(36, 0.3)); //drives 3ft foward approaching game piece
                break;

            case AutonomousConstants.kScoring2: //scoring only in mid position
                addCommands(new NodeScoring(0)); //set arm to score
                addCommands(new TravelDistanceCommand(-200, 0.5)); //drive backwards 16.5 ft (+2 inches to account for going over charge station)
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                addCommands(new GyroTurnCommand(-90)); //turns 90 degree left (relative to starting position)
                addCommands(new TravelDistanceCommand(30, 0.3)); //drives foward
                addCommands(new GyroTurnCommand(-90)); //turns 90 degree left 
                addCommands(new TravelDistanceCommand(60, 0.3)); //drives 5ft foward approaching game piece
                break;

            case AutonomousConstants.kScoring3: //scoring only in right position
                addCommands(new NodeScoring(0));//set arm to score
                addCommands(new TravelDistanceCommand(-200, 0.5)); //drive backwards 16.5 ft 
                addCommands(new PayloadIntake()); 
                addCommands(new DrivePayloadPosition()); //puts arm down
                addCommands(new GyroTurnCommand(180)); //turns robot 180 degrees right relative to starting position (+5 for game piece heading)
                addCommands(new TravelDistanceCommand(36, 0.3)); //drives 3ft foward approaching game piece
                break;
        }
    }
}