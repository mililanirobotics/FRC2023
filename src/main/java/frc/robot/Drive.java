package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder.Type;
import com.revrobotics.SparkMaxRelativeEncoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;


public class Drive {

    // Encoder declarations and initializations 
    // int PULSES_PER_ROTATION = 42;
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

    // Gyro declrations and initializations
    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    Encoder leftEncoder = new Encoder(1, 2, false);
    Encoder rightEncoder = new Encoder(3, 4, false);
    boolean turnDrive = false;
    double currentAngle;

  public Drive() {
    // rFrontEncoder.setInverted(true);
    rightFront.setInverted(true);
    // rBackEncoder.setInverted(true);
    rightBack.setInverted(true);
  }

    public void robotInit() {
  
    }

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

    //This method is called on during autonomous for turning
    public void turnDrive(int timeOut, double turnDegrees, double speed) {
        //set turnDegrees parameters for this method to negative to turn left
        //timeOut parameter should be in milliseconds
        double desiredAngle = currentAngle + turnDegrees;

        //statement above calculates the position of the desired angle
        //based on the robots current orientation
        double timeStarted = System.currentTimeMillis();
        //timeStarted is recorded for timeOut

        double error = desiredAngle - gyro.getAngle();

        System.out.println("Gyro Angle: " + gyro.getAngle());
        System.out.println("Desired Angle: " + desiredAngle);
        System.out.println("Error: " + error);
        //error between desiredAngle and our current angle is established
        if(error < -2 || error > 2) {
        //While loop will continue as long as error is not inbetween the slack range of -3 to 3
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
}
