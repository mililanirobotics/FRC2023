// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//constants
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.Autonomous.GyroEngageAuto;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.TravelDistanceCommand;
import frc.robot.commands.Engage.GyroEngageCommand;
import frc.robot.commands.GyroTurnCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.LimelightSubsystem.Pipeline;
import frc.robot.commands.PivotElbowUpCommand;
import frc.robot.commands.PivotElbowDownCommand;
import frc.robot.commands.ExtendBicepCommand;
import frc.robot.commands.NodeScoring;
import frc.robot.commands.RetractBicepCommand;
import frc.robot.commands.AutoPivotElbowCommand;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ArriveToGrid;
import frc.robot.commands.BicepArmToggleCommand;
import frc.robot.commands.CloseClawCommand;
import frc.robot.commands.DrivePayloadPosition;
import frc.robot.commands.OpenClawCommand;
import frc.robot.commands.PayloadIntake;
import frc.robot.commands.ToggleClawCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer; 

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  //note: if you define commands here, it messed with the command scheduler
  private final GenericHID joystick = new GenericHID(JoystickConstants.kControllerPort);

  private SendableChooser<CommandBase> autoCommand = new SendableChooser<CommandBase>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  private final ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
  private final ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-match");
  private final ShuffleboardTab engagementTab = Shuffleboard.getTab("Engage");

  private final DriveSubsystem driveSubsystem = new DriveSubsystem(motorTab, preMatchTab);
  private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();
  private final ClawSubsystem clawSubsystem = new ClawSubsystem();
  private final ElbowPivotSubsystem elbowPivotSubsystem = new ElbowPivotSubsystem();
  private final BicepArmSubsystem bicepArmSubsystem = new BicepArmSubsystem();
  
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the buttonbindings 
    CommandScheduler.getInstance().schedule(new CloseClawCommand(clawSubsystem));
    CommandScheduler.getInstance().schedule(new RetractBicepCommand(bicepArmSubsystem));
    configureButtonBindings();

    autoCommand.setDefaultOption("Balancing Auto Path", new GyroEngageAuto(driveSubsystem, 24, motorTab));

    autoCommand.addOption("Gyro engage", new GyroEngageCommand(driveSubsystem, engagementTab));
    autoCommand.addOption("Turn drive test", new GyroTurnCommand(driveSubsystem, -90, motorTab));
    autoCommand.addOption("Only moving", new TravelDistanceCommand(36, 0.5, driveSubsystem));
    preMatchTab.add("Auto Paths", autoCommand);

    driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, joystick));

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
    new JoystickButton(joystick, 1).onTrue(new ArriveToGrid(Pipeline.APRIL_TAGS, 17.75, limelightSubsystem, driveSubsystem));
    new JoystickButton(joystick, 1).onTrue(new TravelDistanceCommand(50, .20, driveSubsystem));
    new JoystickButton(joystick, 2).onTrue(new ArriveToGrid(Pipeline.REFLECTIVE_TAPE, 24, limelightSubsystem, driveSubsystem)); 
    new JoystickButton(joystick, 3).onTrue(new DrivePayloadPosition(bicepArmSubsystem));
    new JoystickButton(joystick, 4).onTrue(new PayloadIntake(bicepArmSubsystem, elbowPivotSubsystem));
    new JoystickButton(joystick, 5).onTrue(new NodeScoring(20, bicepArmSubsystem, clawSubsystem, elbowPivotSubsystem, driveSubsystem));
    new JoystickButton(joystick, 6).onTrue(new AutoPivotElbowCommand(10, elbowPivotSubsystem));
    new JoystickButton(joystick, 7).onTrue(new PivotElbowUpCommand(0.35, elbowPivotSubsystem, joystick));
    new JoystickButton(joystick, 8).onTrue(new PivotElbowDownCommand(-0.35, elbowPivotSubsystem, joystick));
    new JoystickButton(joystick, 9).onTrue(new CloseClawCommand(clawSubsystem));
    new JoystickButton(joystick, 10).onTrue(new OpenClawCommand(clawSubsystem));
    new JoystickButton(joystick, 11).onTrue(new ToggleClawCommand(clawSubsystem)); 
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

  public static double limitValue(double value, double upperLimit, double lowerLimit) {
    return Math.max(lowerLimit, Math.min(value, upperLimit));
  }

  // public Command scoreNodeCommand(double angleRotation) {
  //   return Commands.sequence(
  //     new ExtendBicepCommand(bicepArmSubsystem),
  //     new AutoPivotElbowCommand(angleRotation),
  //     new OpenClawCommand(clawSubsystem),
  //     new TravelDistanceCommand(26, 0.35),
  //     new AutoPivotElbowCommand(0)
  //   );
  // }
}
