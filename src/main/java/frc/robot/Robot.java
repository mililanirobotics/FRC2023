// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Apriltags;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  CANSparkMax leftFront = new CANSparkMax(12, MotorType.kBrushless);
  CANSparkMax rightFront = new CANSparkMax(10, MotorType.kBrushless);
  CANSparkMax leftBack = new CANSparkMax(13, MotorType.kBrushless);
  CANSparkMax rightBack = new CANSparkMax(11, MotorType.kBrushless);

  Limelight limeLight = new Limelight();
  Apriltags apriltags = new Apriltags();
  Drive drive = new Drive();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    rightFront.setInverted(true);
    rightBack.setInverted(true);
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

  public void angleAlign() {
    double speed = 0;
    double k = 0.01;

    if (limeLight.getHorizontalDegToTarget() < -1 || limeLight.getHorizontalDegToTarget() > 1) {
      speed = limeLight.getHorizontalDegToTarget() * k;
      if (Math.abs(speed) > 0.5) {
        speed = Math.copySign(0.5, speed);
      }
      else if (Math.abs(speed) < 0.35) {
        speed = Math.copySign(0.35, speed);
      }
      leftFront.set(-speed);
      rightFront.set(speed);
      leftBack.set(-speed);
      rightBack.set(speed);
    }
  else {
    leftFront.stopMotor();
    rightFront.stopMotor();
    leftBack.stopMotor();
    rightBack.stopMotor();
    }
    
  }

  public void alignMovement(double speed, string direction, double timeout) {
    double distance = limeLight.estimateHorizontalDistance() - 16;
    drive.encoderDrive(speed, distance, direction, timeout); 
  }
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
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

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
