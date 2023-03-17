package frc.robot.commands.Misc;

//subsystmes and commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Alignment.AlignmentCommand;
import frc.robot.commands.Drive.LimelightTravelDistanceCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
//general imports
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

//constructor
public class ArriveToGridCommand extends SequentialCommandGroup {
    public ArriveToGridCommand(LimelightSubsystem.Pipeline pipeline, double targetHeight, LimelightSubsystem limelightSubsystem, DriveSubsystem driveSubsystem, ShuffleboardTab motorTab, ShuffleboardTab limelightTab) {
        addCommands(new AlignmentCommand(pipeline, driveSubsystem, limelightSubsystem, limelightTab));
        addCommands(new LimelightTravelDistanceCommand(targetHeight, limelightSubsystem, driveSubsystem, motorTab));
    }
}
