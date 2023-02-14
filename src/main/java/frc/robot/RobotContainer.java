// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//constants
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.Autonomous.GyroEngageAuto;
import frc.robot.commands.Drive.TravelDistanceCommand;
import frc.robot.commands.Engage.AccelerometerEngageCommand;
import frc.robot.commands.Misc.GyroTurnCommand;
import frc.robot.commands.Autonomous.AccelerometerEngageAuto;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  //note: if you define commands here, it messed with the command scheduler
  public final static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public final static LimelightSubsystem limelightSubsystem = new LimelightSubsystem();

  public final static GenericHID joystick = new GenericHID(JoystickConstants.kControllerPort);
  private SendableChooser<CommandBase> autoCommand = new SendableChooser<CommandBase>();

  public static ShuffleboardTab motorTab = Shuffleboard.getTab("Motors");
  public static ShuffleboardTab preMatchTab = Shuffleboard.getTab("Pre-match");
  public static ShuffleboardTab engagementTab = Shuffleboard.getTab("Engage");
  
  public static GenericEntry leftEncoderWidget = motorTab.add("Left Encoder", driveSubsystem.getLeftEncoder()).withSize(2, 1).getEntry();
  public static GenericEntry rightEncoderWidget = motorTab.add("Right Encoder", driveSubsystem.getRightEncoder()).withSize(2, 1).getEntry();  

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings  
    configureButtonBindings();

    autoCommand.setDefaultOption("Balancing Auto Path", new GyroEngageAuto());
    autoCommand.addOption("Accelerometer engage", new AccelerometerEngageCommand());
    autoCommand.addOption("Turn drive test", new GyroTurnCommand(-180));
    autoCommand.addOption("Only moving", new TravelDistanceCommand(36, 0.25));
    SmartDashboard.putData("Auto Paths", autoCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
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
}
