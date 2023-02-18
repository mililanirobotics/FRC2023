package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;


public class Drive {

    // Encoder declarations and initializations 
    int COUNTS_PER_ROTATION = 42;
    double DRIVE_GEAR_REDUCTION = 8.6;
    double WHEEL_DIAMETER = 4;
    double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    double COUNTS_PER_INCH = (COUNTS_PER_ROTATION * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE;
    CANSparkMax leftFront = new CANSparkMax(12, MotorType.kBrushless);
    CANSparkMax rightFront = new CANSparkMax(10, MotorType.kBrushless);
    CANSparkMax leftBack = new CANSparkMax(13, MotorType.kBrushless);
    CANSparkMax rightBack = new CANSparkMax(11, MotorType.kBrushless);
    RelativeEncoder lFrontEncoder = leftFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);
    RelativeEncoder rFrontEncoder = rightFront.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);
    RelativeEncoder lBackEncoder = leftBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);
    RelativeEncoder rBackEncoder = rightBack.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);
    boolean eDriveDone = false;
    boolean alignDone = false;

    // Gyro declrations and initializations
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    Encoder leftEncoder = new Encoder(1, 2, false);
    Encoder rightEncoder = new Encoder(3, 4, false);
    boolean turnDrive = false;
    double currentAngle;

    // Limelight 
    Apriltags aprilTags = new Apriltags();

  public Drive() {
    // rFrontEncoder.setInverted(true);
    rightFront.setInverted(true);
    // rBackEncoder.setInverted(true);
    rightBack.setInverted(true);
  }

  public void robotInit() {

  }

    /**
     * This method is called upon in autonomous for moving back and forth
     * @param speed sets speed the robot will move at
     * @param distance determines how far the robot will move (USE INCHES)
     * @param direction determines which way the robot will move ("forward" or "backward")
     * @param timeOut determines how long the method will run before being stopped forcefully (USE MILLISECONDS)
     */
    public void encoderDrive(double speed, double distance, String direction, double timeOut) {
        int motorTarget = (int)(distance * COUNTS_PER_INCH);
        eDriveDone = false;
        
        double timeStarted = System.currentTimeMillis();
    
        lFrontEncoder.setPosition(0);
        rFrontEncoder.setPosition(0);
        lBackEncoder.setPosition(0);
        rBackEncoder.setPosition(0);
    
        if (direction == "forward") {
          leftFront.set(speed);
          rightFront.set(speed);
          leftBack.set(speed);
          rightBack.set(speed);  
        }
        else if (direction == "backward") {
          leftFront.set(-speed);
          rightFront.set(-speed);
          leftBack.set(-speed);
          rightBack.set(-speed);
        }
        
        if(Math.abs(lFrontEncoder.getPosition()) >= motorTarget || Math.abs(rFrontEncoder.getPosition()) >= motorTarget || Math.abs(lBackEncoder.getPosition()) >= motorTarget || Math.abs(rBackEncoder.getPosition()) >= motorTarget || System.currentTimeMillis() > (timeStarted + timeOut)) {
          leftFront.set(0);
          rightFront.set(0);
          leftBack.set(0);
          rightBack.set(0);
          eDriveDone = true;
        }
    
      }

    /**
     * This method is called on during autonomous for turning
     * @param speed sets the speed the robot will turn at
     * @param turnDegrees amount of degrees from the current orientation the robot will turn (POSITIVE = RIGHT, NEGATIVE = LEFT)
     * @param timeOut determines how long the method will run before being forcefully stopped (USE MILLISECONDS)
     */
    public void turnDrive(double speed, double turnDegrees, int timeOut) {
        //set turnDegrees parameters for this method to negative to turn left
        //timeOut parameter should be in milliseconds
        double desiredAngle = gyro.getAngle() + turnDegrees;

        //statement above calculates the position of the desired angle
        //based on the robots current orientation
        //double timeStarted = System.currentTimeMillis();
        //timeStarted is recorded for timeOut

        double error = desiredAngle - gyro.getAngle();

        System.out.println("Gyro Angle: " + gyro.getAngle());
        System.out.println("Desired Angle: " + desiredAngle);
        System.out.println("Error: " + error);
        //error between desiredAngle and our current angle is established
        if(error < -2 || error > 2) {
          //Loop will continue as long as error is not inbetween the slack range of -2 to 2
          error = desiredAngle - gyro.getAngle();
          //This calculates at the start of every loop to determine which way the robot will turn
          if(error > 0) {
              //If error is positive, the robot turns right
              leftFront.set(speed);
              rightFront.set(-speed);
              leftBack.set(speed);
              rightBack.set(-speed);
          }
          else {
              //If error is negative, the robot turns left
              leftFront.set(-speed);
              rightFront.set(speed);
              leftBack.set(-speed);
              rightBack.set(speed);
          }
        }
        else {
          leftFront.stopMotor();
          rightFront.stopMotor();
          leftBack.stopMotor();
          rightBack.stopMotor();
          turnDrive = true;
        }

        //Stop all motors
    }

  public double getAngle()
  {
      return gyro.getAngle();
  }

    // public void log() {
    //   SmartDashboard.putNumber("Left front encoder value", leftFront.getEncoder().getPosition());
    //   SmartDashboard.putNumber("Right front encoder value", rightFront.getEncoder().getPosition());
    //   SmartDashboard.putNumber("timer", System.currentTimeMillis());
    // }



}
  