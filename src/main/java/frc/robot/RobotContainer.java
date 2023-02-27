// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants.JoystickConstants;
import frc.robot.subsystems.BicepArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowPivotSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

import frc.robot.commands.PivotElbowUpCommand;
import frc.robot.commands.PivotElbowDownCommand;
import frc.robot.commands.ExtendBicepCommand;
import frc.robot.commands.RetractBicepCommand;
import frc.robot.commands.AutoPivotElbowCommand;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ArriveToGrid;
import frc.robot.commands.BicepArmToggleCommand;

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
  public final static ElbowPivotSubsystem elbowPivotSubsystem = new ElbowPivotSubsystem();
  public final static BicepArmSubsystem bicepArmSubsystem = new BicepArmSubsystem();


  public final static GenericHID joystick = new GenericHID(JoystickConstants.kControllerPort);
  private SendableChooser<CommandBase> autoCommand = new SendableChooser<CommandBase>();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings  
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(joystick, 1).onTrue(new ArriveToGrid(3, 17.75));
    new JoystickButton(joystick, 2).onTrue(new ArriveToGrid(3, 17.75)); 
    // new JoystickButton(joystick, 3).onTrue(new ExtendBicepCommand());
    // new JoystickButton(joystick, 4).onTrue(new RetractBicepCommand());
    // new JoystickButton(joystick, 5).onTrue(new BicepArmToggleCommand());
    new JoystickButton(joystick, 6).onTrue(new AutoPivotElbowCommand(10));
    new JoystickButton(joystick, 7).onTrue(new PivotElbowUpCommand(-0.35));
    new JoystickButton(joystick, 8).onTrue(new PivotElbowDownCommand(-0.35));
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
