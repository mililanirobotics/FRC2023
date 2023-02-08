// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kDockAndEngage1 = "Dock and Engage Left";
  private static final String kDockAndEngage2 = "Dock and Engage Middle";
  private static final String kDockAndEngage3 = "Dock and Engage Right";
  private static final String kScoring1 = "Scoring on the Left";
  private static final String kScoring2 = "Scoring in the Middle";
  private static final String kScoring3 = "Scoring on the Right";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  
  Drive drive = new Drive();
  PivotArm pivotArm = new PivotArm();

  Joystick joystick = new Joystick(0);
  Claw claw = new Claw();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    m_chooser.addOption("Dock and Engage left", kDockAndEngage1);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    drive.rightFront.setInverted(true);
    drive.rightBack.setInverted(true);
    
    claw.armExtension.set(Value.kReverse);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}
  
  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  // Angle rotations for pivot arm may have to be changed 
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
      case kDockAndEngage1: 
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(35);
        claw.clawToggle();
        drive.encoderDrive(0.4, 136.5, "Backward", 5000);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(1500, -90, 0.13);
        drive.encoderDrive(0.3, 36, "Forward", 3000);
        drive.turnDrive(1500, 90, 0.13);
        drive.encoderDrive(0.3, 40, "Forward", 3000);
        // Dock and Engage code
        break;
      case kDockAndEngage2:
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(30);
        claw.clawToggle();
        drive.encoderDrive(0.3, 20, "Backward", 1500);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(2000, 180, 0.13);
        drive.encoderDrive(0.4, 35, "Forward", 3500);
        // Dock and Engage code
        break;
      case kDockAndEngage3:
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(35);
        claw.clawToggle();
        drive.encoderDrive(0.4, 136.5, "Backward", 5000);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(1500, 90, 0.13);
        drive.encoderDrive(0.3, 36, "Forward", 3000);
        drive.turnDrive(1500, -90, 0.13);
        drive.encoderDrive(0.3, 40, "Forward", 3000);
        // Dock and Engage code
        break;
      case kScoring1:
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(35);
        claw.clawToggle();
        drive.encoderDrive(0.4, 136.5, "Backward", 5000);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(1500, -90, 0.13);
        drive.encoderDrive(0.3, 24, "Forward", 3000);
        drive.turnDrive(1500, -90, 0.13);
        drive.encoderDrive(0.3, 36, "Forward", 3000);
        break;
      case kScoring2:
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(30);
        claw.clawToggle();
        drive.encoderDrive(0.3, 20, "Backward", 1500);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(-1500, -90, 0.13);
        drive.encoderDrive(0.3, 30, "Forward", 3500);
        drive.turnDrive(-1500, -90, 0.13);
        drive.encoderDrive(0.3, 36, "Forward", 3000);
        drive.turnDrive(1500, -90, 0.13);
        drive.encoderDrive(0.3, 36, "Forward", 3000);
        drive.turnDrive(1500, 90, 0.13);
        drive.encoderDrive(0.3, 40, "Forward", 4000);
        break;
      case kScoring3:
        drive.encoderDrive(0.3, 12, "Forward", 1000);
        pivotArm.AutoEncoderRotation(35);
        claw.clawToggle();
        drive.encoderDrive(0.4, 136.5, "Backward", 5000);
        pivotArm.AutoEncoderRotation(0);
        drive.turnDrive(1500, 90, 0.13);
        drive.encoderDrive(0.3, 20, "Forward", 2000);
        drive.turnDrive(1500, 90, 0.13);
        drive.encoderDrive(0.3, 40, "Forward", 3000);
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
