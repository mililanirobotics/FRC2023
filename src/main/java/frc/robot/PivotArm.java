package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;
import edu.wpi.first.wpilibj.Joystick;



public class PivotArm {
    int COUNTS_PER_ROTATION = 42;
    double speed = 0;
    double k = 0.01;
    double gearRatio = 180; // Need to know gear Ratio 
    double angleRotation;

    Joystick joystick = new Joystick(0);
    // CAN ID is subject to change
    CANSparkMax clawPivot = new CANSparkMax(16, MotorType.kBrushless);
    RelativeEncoder pivotEncoder = clawPivot.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);

    public void robotInit () {
        clawPivot.set(0);
        pivotEncoder.setPosition(0);
    }

    // Autonomous method for pivoting the arm
    public void AutoEncoderRotation(double angleRotation) {
    // W.I.P. motor target. angleRotation is (x degrees / 360)
    int motorTarget = (int)(gearRatio * COUNTS_PER_ROTATION * angleRotation/360);
    double error = motorTarget - pivotEncoder.getPosition();
    
    // Pivoting the arm to target
    if (angleRotation == 0) {
        if (pivotEncoder.getPosition() > 1) {
            clawPivot.set(-0.4);
        }
        else {
            clawPivot.set(0);
        }
    }
    else if (angleRotation > 1) {
        if (error > 1 || error < -1) {

            speed = error * k;
            if (Math.abs(speed) > 0.5) {
                speed = Math.copySign(0.5, speed);
            }
            else if (Math.abs(speed) > 0.2) {
                speed = Math.copySign(0.2, speed);
            }

            clawPivot.set(speed);

            } 
        else {

        // Stalling the pivot motor may need to change its method
            clawPivot.set(0);

            }
        } 
    }
    


    // public void EncoderRotation() {

    // if (joystick.getRawButtonPressed(3)) {
    //     // Placeholder angle for rotating arm to cube node
    //     angleRotation = 30;
    // }
    // else if (joystick.getRawButtonPressed(4)) {
    //     // Placeholder angle for rotating arm to cone node
    //     angleRotation = 35;
    // }

    // // W.I.P. motor target. angleRotation is (x degrees / 360)
    // double motorTarget = (int) gearRatio * COUNTS_PER_ROTATION * angleRotation/360;
    // double error = motorTarget - pivotEncoder.getPosition();
    
    // // Pivoting the arm to target
    // if (error > 1 || error < -1) {

    //     speed = error * k;
    //     if (Math.abs(speed) > 0.5) {
    //         speed = Math.copySign(0.5, speed);
    //     }
    //     else if (Math.abs(speed) > 0.2) {
    //         speed = Math.copySign(0.2, speed);
    //     }

    //     clawPivot.set(speed);

    //     } 
    // else {

    //     // Stalling the pivot motor may need to change its method
    //     clawPivot.set(0);

    //     }
    // }

}
