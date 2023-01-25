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

public class Drive {
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
}
