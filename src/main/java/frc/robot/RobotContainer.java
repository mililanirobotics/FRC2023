// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//subsystems and commands
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.LimelightSubsystem.Pipeline;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.Claw.CloseClawCommand;
import frc.robot.commands.Claw.ToggleClawCommand;
import frc.robot.commands.Alignment.AlignmentCommand;
import frc.robot.commands.Arm.AutoPivotElbowCommand;
import frc.robot.commands.Arm.BicepArmToggleCommand;
import frc.robot.commands.Arm.ExtendBicepCommand;
import frc.robot.commands.Arm.IntakePositionCommand;
import frc.robot.commands.Arm.ManualPivotCommand;
import frc.robot.commands.Drive.DriveCommand;
import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.GyroEngageCommand;
import frc.robot.commands.Misc.ArriveToGridCommand;
import frc.robot.commands.Misc.GyroTurnCommand;
import frc.robot.commands.Misc.NodeScoringCommand;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer; 
//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.JoystickConstants;





/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //initializing the gamepads used
  private final GenericHID primaryJoystick = new GenericHID(JoystickConstants.kPrimaryPort);
  private final GenericHID secondaryJoystick = new GenericHID(JoystickConstants.kSecondaryPort);

  //allows the user to select which auto path they would like to use
  private SendableChooser<CommandBase> autoCommand = new SendableChooser<CommandBase>();

  //initializing all of our different tabs on shuffleboard
  private final ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
  private final ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-match");
  private final ShuffleboardTab engagementTab = Shuffleboard.getTab("Engage");

  //initializing all of our subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(motorTab, preMatchTab);
  private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
  private final ClawSubsystem clawSubsystem = new ClawSubsystem();
  private final ElbowPivotSubsystem elbowPivotSubsystem = new ElbowPivotSubsystem();
  private final BicepArmSubsystem bicepArmSubsystem = new BicepArmSubsystem();
  
  //constructor
  public RobotContainer() {
    //configure the buttonbindings 
    configureButtonBindings();

    //closes the claw and pulls the arm into the frame upon initialization (starting position)
    CommandScheduler.getInstance().schedule(new CloseClawCommand(clawSubsystem));
    CommandScheduler.getInstance().schedule(new ExtendBicepCommand(bicepArmSubsystem));

    //adding all of our autopaths as options
    autoCommand.addOption("Gyro engage", new GyroEngageCommand(driveSubsystem, engagementTab));
    autoCommand.addOption("Turn drive test", new GyroTurnCommand(driveSubsystem, -90, motorTab));
    autoCommand.addOption("Travel Distance Test", new TravelDistanceCommand(36, driveSubsystem, motorTab));
    autoCommand.addOption("Auto Pivot Test", new AutoPivotElbowCommand(0, elbowPivotSubsystem));
    autoCommand.addOption("Auto Scoring Test", new ArriveToGridCommand(Pipeline.APRIL_TAGS , GameConstants.kAprilTagHeight, limelightSubsystem, driveSubsystem, motorTab));

    //adding the selectable chooser to shuffleboard
    preMatchTab.add("Auto Paths", autoCommand);

    //setting the default command during Tele-op to DriveCommand
    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, primaryJoystick));

    //reseting the encoders and gyros during robot startup
    driveSubsystem.resetEncoders();
    driveSubsystem.zeroOutGyro();
    driveSubsystem.calibrateGyro();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //=========================================================================== 
    // primary gamepads
    //===========================================================================

    //gyro engage (B)
    new JoystickButton(primaryJoystick, JoystickConstants.kBButtonPort).onTrue(new GyroEngageCommand(driveSubsystem, engagementTab));

    //Align with april tags (left bumper)
    new JoystickButton(primaryJoystick, JoystickConstants.kLeftBumperPort).onTrue(new AlignmentCommand(Pipeline.REFLECTIVE_TAPE, driveSubsystem, limelightSubsystem));
    
    //Align with reflective tape (right bumper)
    new JoystickButton(primaryJoystick, JoystickConstants.kRightBumperPort).onTrue(new AlignmentCommand(Pipeline.APRIL_TAGS, driveSubsystem, limelightSubsystem));
    
    //=========================================================================== 
    // secondary gamepads
    //===========================================================================

    // **** need finalized angles 
    //auto pivot to low scoring position (A)
    new JoystickButton(secondaryJoystick, JoystickConstants.kAButtonPort).onTrue(new AutoPivotElbowCommand(0, elbowPivotSubsystem));

    //auto pivot to the medium cube position (B)
    new JoystickButton(secondaryJoystick, JoystickConstants.kBButtonPort).onTrue(
        new ArriveToGridCommand(Pipeline.APRIL_TAGS, GameConstants.kAprilTagHeight, limelightSubsystem, driveSubsystem, motorTab)
          .andThen(new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem)));

    //auto pivots to the medium cone position (X)
    new JoystickButton(secondaryJoystick, JoystickConstants.kXButtonPort).onTrue(
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab)
        .andThen(new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem)));

    //auto pivots to the slider position (Y)
    new JoystickButton(secondaryJoystick, JoystickConstants.kYButtonPort).onTrue(new AutoPivotElbowCommand(0, elbowPivotSubsystem));

    //moves the bicep into the driving position (left trigger)
    new Trigger(() -> secondaryJoystick.getRawAxis(JoystickConstants.kLeftTriggerPort) >= 0.5).onTrue(new ExtendBicepCommand(bicepArmSubsystem));

    //moves the bicep into the intake position (right trigger)
    new Trigger(() -> secondaryJoystick.getRawAxis(JoystickConstants.kRightTriggerPort) >= 0.5).onTrue(new IntakePositionCommand(bicepArmSubsystem, elbowPivotSubsystem));

    //toggles the bicep piston (left bumper)
    new JoystickButton(secondaryJoystick, JoystickConstants.kLeftBumperPort).onTrue(new BicepArmToggleCommand(bicepArmSubsystem));

    //toggles the claw pistons (right bumper)
    new JoystickButton(secondaryJoystick, JoystickConstants.kRightBumperPort).onTrue(new ToggleClawCommand(clawSubsystem));

    //pivots the forearm up and down (left joystick)
    new Trigger(() -> Math.abs(secondaryJoystick.getRawAxis(JoystickConstants.kLeftYJoystickPort)) >= 0.2).onTrue(new ManualPivotCommand(secondaryJoystick, elbowPivotSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand.getSelected();
  }

  /**
   * Returns the limited speed that is between the lower limit and the upper limit
   * @param currentSpeed The current speed calculated 
   * @param lowerLimit The minimum speed that is desired
   * @param upperLimit The maximum speed that is desired
   * @return The adjusted speed that is between the lower and upper limits
   */
  public static double limitSpeed(double currentSpeed, double lowerLimit, double upperLimit) {
    return Math.copySign(Math.max(lowerLimit, Math.min(Math.abs(currentSpeed), upperLimit)), currentSpeed);
  }

  //=========================================================================== 
  // auto paths
  //===========================================================================

  /**
   * Runs the auto path for the left side blue alliance and right side red alliance (engage)
   * @return The sequential command group that runs the auto path
   */
  public Command dockEngageLeft() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //retracts the bicep back into the frame
      new ExtendBicepCommand(bicepArmSubsystem), 
      //travels outside of the community to earn mobility points
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns the robot right 90 degrees (relative to starting position)
      new GyroTurnCommand(driveSubsystem, -90, motorTab),
      //drives foward 5ft
      new TravelDistanceCommand(60, driveSubsystem, motorTab),
      //turns the robot left 90 degrees
      new GyroTurnCommand(driveSubsystem, 90, motorTab),
      //drives foward 3 ft to get onto the charge station
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //starts engagement command
      new GyroEngageCommand(driveSubsystem, engagementTab)
    );
  }

  /**
   * Runs the auto path for the middle position for both alliances (engage)
   * @return The sequential command group that runs the auto path
   */
  public Command dockEngageMiddle() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //retracts the bicep back into the frame
      new ExtendBicepCommand(bicepArmSubsystem), 
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      new GyroEngageCommand(driveSubsystem, engagementTab)
    );
  }

  /**
   * Runs the auto path for the right side blue alliance and left side red alliance (engage)
   * @return The sequential command group that runs the auto path
   */
  public Command dockEngageRight() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //retracts the bicep back into the frame
      new ExtendBicepCommand(bicepArmSubsystem), 
      //travels outside of the community to earn mobility points
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns the robot left 90 degrees (relative to starting position)
      new GyroTurnCommand(driveSubsystem, 90, motorTab),
      //drives foward 5ft
      new TravelDistanceCommand(60, driveSubsystem, motorTab),
      //turns the robot right 90 degrees
      new GyroTurnCommand(driveSubsystem, -90, motorTab),
      //drives foward 3 ft to get onto the charge station
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //starts engagement command
      new GyroEngageCommand(driveSubsystem, engagementTab)
    );
  }

   /**
   * Runs the auto path for the left side blue alliance and right side red alliance (scoring)
   * @return The sequential command group that runs the auto path
   */
  public Command scoringLeft() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //brings the arm back into the frame
      new ExtendBicepCommand(bicepArmSubsystem),
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns 180 degrees to face the game pieces on the field
      new GyroTurnCommand(driveSubsystem, 180, motorTab),
      //moves 3ft closer to the game pieces 
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //goes back to the intake position
      new IntakePositionCommand(bicepArmSubsystem, elbowPivotSubsystem)
    );
  }

  /**
   * Runs the auto path for the middle position for both alliances (scoring)
   * @return The sequential command group that runs the auto path
   */
  public Command scoringMiddle() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //brings the arm back into the frame
      new ExtendBicepCommand(bicepArmSubsystem),
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns 90 degree left (relative to starting position)t 
      new GyroTurnCommand(driveSubsystem, -90, motorTab),
      //drives foward 30 inches 
      new TravelDistanceCommand(30, driveSubsystem, motorTab),
      //turns 90 degree left (relative to starting position)t 
      new GyroTurnCommand(driveSubsystem, -90, motorTab),
      //drives 5ft foward approaching game piece
      new TravelDistanceCommand(60, driveSubsystem, motorTab), 
      //goes back to the intake position
      new IntakePositionCommand(bicepArmSubsystem, elbowPivotSubsystem)
    );
  }

   /**
   * Runs the auto path for the right side blue alliance and left side red alliance (scoring)
   * @return The sequential command group that runs the auto path
   */
  public Command scoringRight() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem),
      //brings the arm back into the frame
      new ExtendBicepCommand(bicepArmSubsystem),
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns 180 degrees to face the game pieces on the field
      new GyroTurnCommand(driveSubsystem, 180, motorTab),
      //moves 3ft closer to the game pieces 
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //goes back to the intake position
      new IntakePositionCommand(bicepArmSubsystem, elbowPivotSubsystem)
    );
  }
}
