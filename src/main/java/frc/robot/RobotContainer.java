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
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Claw.CloseClawCommand;
import frc.robot.commands.Claw.ToggleClawCommand;
import frc.robot.commands.Alignment.AlignmentCommand;
import frc.robot.commands.Arm.AutoPivotElbowCommand;
import frc.robot.commands.Arm.ExtendBicepCommand;
import frc.robot.commands.Arm.ManualPivotCommand;
import frc.robot.commands.Arm.PivotDegreesCommand;
import frc.robot.commands.Arm.RetractBicepCommand;
import frc.robot.commands.Drive.DriveCommand;
import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.GyroEngageCommand;
import frc.robot.commands.Misc.ArriveToGridCommand;
import frc.robot.commands.Misc.GyroTurnCommand;
import frc.robot.commands.Misc.NodeScoringCommand;
//general imports
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer; 
//constants
import frc.robot.Constants.GameConstants;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.PivotConstants;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //initializing the gamepads used
  private final GenericHID primaryLeftStick = new GenericHID(JoystickConstants.kPrimaryLeftStickPort);
  private final GenericHID primaryRightStick = new GenericHID(JoystickConstants.kPrimaryRightStickPort);
  private final GenericHID secondaryJoystick = new GenericHID(JoystickConstants.kSecondaryPort);

  //allows the user to select which auto path they would like to use
  private SendableChooser<CommandBase> autoCommand = new SendableChooser<CommandBase>();

  //initializing all of our testing tabs on shuffleboard
  private final ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
  private final ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-match");
  private final ShuffleboardTab engagementTab = Shuffleboard.getTab("Engage");
  private final ShuffleboardTab limelightTab = Shuffleboard.getTab("Limelight");
  private final ShuffleboardTab armTab = Shuffleboard.getTab("Arm");

  //initializing all of our subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(motorTab, preMatchTab);
  private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
  private final ClawSubsystem clawSubsystem = new ClawSubsystem();
  private final ElbowPivotSubsystem elbowPivotSubsystem = new ElbowPivotSubsystem(armTab);
  private final BicepArmSubsystem bicepArmSubsystem = new BicepArmSubsystem(armTab);
  
  //constructor
  public RobotContainer() {
    //configure the buttonbindings 
    configureButtonBindings();

    //closes the claw and pulls the arm into the frame upon initialization (starting position)
    CommandScheduler.getInstance().schedule(new ExtendBicepCommand(bicepArmSubsystem).andThen(new CloseClawCommand(clawSubsystem)));

    //adding our test autopaths as options
    autoCommand.addOption("Gyro engage", new GyroEngageCommand(driveSubsystem, engagementTab));
    autoCommand.addOption("Turn drive test", new GyroTurnCommand(driveSubsystem, 180, motorTab));
    autoCommand.addOption("Travel Distance Test", new TravelDistanceCommand(108, driveSubsystem, motorTab));
    autoCommand.addOption("Auto Pivot Test", new AutoPivotElbowCommand(0, elbowPivotSubsystem, armTab));
    autoCommand.addOption("Auto Scoring Test", new ArriveToGridCommand(Pipeline.APRIL_TAGS , GameConstants.kAprilTagHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab));

    //adding official auto paths
    autoCommand.addOption("Blue Alliance Left and Red Alliance Right (Engage)", dockEngageLeft());
    autoCommand.addOption("Middle Both Alliances (Engage)", dockEngageMiddle());
    autoCommand.addOption("Blue Alliance Right and Red Alliance Left (Engage)", dockEngageRight());
    autoCommand.addOption("Blue Alliance Left and Red Alliance Right (Score)", scoringLeft());
    autoCommand.addOption("Middle Both Alliances (Score", scoringMiddle());
    autoCommand.addOption("Blue Alliance Right and Red Alliance Right (Score)", scoringRight());

    //adding the selectable chooser to shuffleboard
    preMatchTab.add("Auto Paths", autoCommand);

    //setting the default command during Tele-op to DriveCommand
    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, primaryLeftStick, primaryRightStick));

    //reseting the encoders and gyros during robot startup
    elbowPivotSubsystem.resetEncoders();
    driveSubsystem.resetEncoders();
    driveSubsystem.zeroOutGyro();
    driveSubsystem.calibrateGyro();

    PowerDistribution pdp = new PowerDistribution();
    motorTab.add("PDP", pdp).withWidget(BuiltInWidgets.kPowerDistribution).withPosition(0, 0).withSize(6, 3);
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

    //Align with april tags (Button 2 on the left primary joystick)
    new JoystickButton(primaryLeftStick, JoystickConstants.kAttackButtonTwo).onTrue(
      new AlignmentCommand(
        Pipeline.APRIL_TAGS, driveSubsystem, limelightSubsystem, limelightTab
      ).until(
        () -> primaryRightStick.getRawButton(JoystickConstants.kAttackTriggerPort)
      )
    );
    
    //Align with reflective tape (Button 2 on the right primary joystick)
    new JoystickButton(primaryRightStick, JoystickConstants.kAttackButtonTwo).onTrue(
      new AlignmentCommand(
        Pipeline.REFLECTIVE_TAPE, driveSubsystem, limelightSubsystem, limelightTab
      ).until(
        () -> primaryRightStick.getRawButton(JoystickConstants.kAttackTriggerPort)
      )
    );
    
    //=========================================================================== 
    // secondary gamepads
    //===========================================================================

    // **** need finalized angles 
    //auto pivot to low scoring position (A)
    new JoystickButton(secondaryJoystick, JoystickConstants.kAButtonPort)
      .onTrue(
        new ConditionalCommand(
          new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab),
          new SequentialCommandGroup(
            new ExtendBicepCommand(bicepArmSubsystem),
            new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab)
          ),
          bicepArmSubsystem::outsideFrame
        )
      );

    //auto pivot to the medium cube position (B)
    // new JoystickButton(secondaryJoystick, JoystickConstants.kBButtonPort).onTrue(
    //     new ArriveToGridCommand(Pipeline.APRIL_TAGS, GameConstants.kAprilTagHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab)
    //       .andThen(new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab)));
    new JoystickButton(secondaryJoystick, JoystickConstants.kBButtonPort)
      .onTrue(
        new ConditionalCommand(
          new AutoPivotElbowCommand(PivotConstants.kCubeAngle, elbowPivotSubsystem, armTab),
          new SequentialCommandGroup(
            new ExtendBicepCommand(bicepArmSubsystem),
            new AutoPivotElbowCommand(PivotConstants.kCubeAngle, elbowPivotSubsystem, armTab)
          ),
          bicepArmSubsystem::outsideFrame
        )
      );

    //auto pivots to the medium cone position (X)
    // new JoystickButton(secondaryJoystick, JoystickConstants.kXButtonPort).onTrue(
    //   new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab)
    //     .andThen(new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab)));
    new JoystickButton(secondaryJoystick, JoystickConstants.kXButtonPort)
      .onTrue(
        new ConditionalCommand(
          new AutoPivotElbowCommand(PivotConstants.kConeAngle, elbowPivotSubsystem, armTab),
          new SequentialCommandGroup(
            new ExtendBicepCommand(bicepArmSubsystem),
            new AutoPivotElbowCommand(PivotConstants.kConeAngle, elbowPivotSubsystem, armTab)
          ),
          bicepArmSubsystem::outsideFrame
        )
      );
    
    //toggles the claw pistons (left bumper)
    new JoystickButton(secondaryJoystick, JoystickConstants.kLeftBumperPort).onTrue(new ToggleClawCommand(clawSubsystem, bicepArmSubsystem));

    //toggles the bicep piston (right bumper)
    new JoystickButton(secondaryJoystick, JoystickConstants.kRightBumperPort)
      .onTrue(
        new ConditionalCommand(
          new SequentialCommandGroup(
            new CloseClawCommand(clawSubsystem), 
            new AutoPivotElbowCommand(PivotConstants.kRetractAngle, elbowPivotSubsystem, armTab),
            new RetractBicepCommand(bicepArmSubsystem),
            new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab)
          ), 
          new ExtendBicepCommand(bicepArmSubsystem),
          bicepArmSubsystem::outsideFrame
        )
        .unless(
          bicepArmSubsystem::getSafety
        )
      );
      
    //pivots the forearm up and down (left joystick)
    new Trigger(() -> Math.abs(secondaryJoystick.getRawAxis(JoystickConstants.kLeftYJoystickPort)) >= JoystickConstants.kDeadzone)
      .onTrue(
        new ManualPivotCommand(secondaryJoystick, elbowPivotSubsystem)
          
      );

    //pivots the arm 2 degrees up (Dpad up)
    new POVButton(secondaryJoystick, JoystickConstants.kDpadUp)
      .onTrue(
        new PivotDegreesCommand(PivotConstants.kAdjustmentAngle, elbowPivotSubsystem, armTab)
      );

    //pivots the arm 2 degrees down (Dpad down)
    new POVButton(secondaryJoystick, JoystickConstants.kDpadDown)
      .onTrue(
        new PivotDegreesCommand(-PivotConstants.kAdjustmentAngle, elbowPivotSubsystem, armTab)
      );
    
    new JoystickButton(secondaryJoystick, JoystickConstants.kStartButtonPort)
      .onTrue(
        new InstantCommand(bicepArmSubsystem::toggleSafety, bicepArmSubsystem)
      );

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
  public CommandBase dockEngageLeft() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
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
  public CommandBase dockEngageMiddle() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
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
  public CommandBase dockEngageRight() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
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
  public CommandBase scoringLeft() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
      //brings the arm back into the frame
      new ExtendBicepCommand(bicepArmSubsystem),
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns 180 degrees to face the game pieces on the field
      new GyroTurnCommand(driveSubsystem, 180, motorTab),
      //moves 3ft closer to the game pieces 
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //goes back to the intake position
      new ParallelCommandGroup(
        new ExtendBicepCommand(bicepArmSubsystem),
        new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab)
      )
    );
  }

  /**
   * Runs the auto path for the middle position for both alliances (scoring)
   * @return The sequential command group that runs the auto path
   */
  public CommandBase scoringMiddle() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
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
      new ParallelCommandGroup(
        new ExtendBicepCommand(bicepArmSubsystem),
        new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab)
      )
    );
  }

   /**
   * Runs the auto path for the right side blue alliance and left side red alliance (scoring)
   * @return The sequential command group that runs the auto path
   */
  public CommandBase scoringRight() {
    return Commands.sequence(
      //auto scores the cone at the beginning of the match
      new ArriveToGridCommand(Pipeline.REFLECTIVE_TAPE, GameConstants.kReflectiveTapeHeight, limelightSubsystem, driveSubsystem, motorTab, limelightTab),
      new NodeScoringCommand(0, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, armTab),
      //brings the arm back into the frame
      new ExtendBicepCommand(bicepArmSubsystem),
      //travels outside of the community for mobility
      new TravelDistanceCommand(-200, driveSubsystem, motorTab),
      //turns 180 degrees to face the game pieces on the field
      new GyroTurnCommand(driveSubsystem, 180, motorTab),
      //moves 3ft closer to the game pieces 
      new TravelDistanceCommand(36, driveSubsystem, motorTab),
      //goes back to the intake position
      new ParallelCommandGroup(
        new ExtendBicepCommand(bicepArmSubsystem),
        new AutoPivotElbowCommand(PivotConstants.kStandardAngle, elbowPivotSubsystem, armTab)
      )
    );
  }
}
