package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;



public class PivotArm {
    int COUNTS_PER_ROTATION = 42;
    double speed = 0;
    double k = 0.01;
    double gearRatio = 180; 
    double angleRotation;

    int armState;
    double armPosition;

    Joystick joystick = new Joystick(0);
    // Intializing Motors and Motor Properties. CAN ID is subject to change
    DoubleSolenoid bicepPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    CANSparkMax leftElbowMotor = new CANSparkMax(16, MotorType.kBrushless);
    CANSparkMax rightElbowMotor = new CANSparkMax(17, MotorType.kBrushless);

    MotorControllerGroup elbowPivot = new MotorControllerGroup(leftElbowMotor, rightElbowMotor);
    RelativeEncoder elbowEncoder = leftElbowMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, COUNTS_PER_ROTATION);

    public PivotArm() {
        rightElbowMotor.setInverted(true);
    }

    public void robotInit () {
        elbowPivot.set(0);
        elbowEncoder.setPosition(0);
    }

    // Toggling extension and retraction of bicep's piston
    public void bicepToggle() {
        bicepPiston.toggle();
    }

    /**
     * Autonomous method for pivoting the arm to a set location
     * angleRotation is (x degrees / 360). angleRotation = 0 is for ground position
     */
    public void AutoEncoderRotation(double angleRotation) {
    int motorTarget = (int)(gearRatio * COUNTS_PER_ROTATION * angleRotation/360);
    double error = motorTarget - elbowEncoder.getPosition();
    
    // Setting the elbow pivot to ground position
    if (angleRotation == 0) {
        if (elbowEncoder.getPosition() > 1) {
            elbowPivot.set(-0.4);
        }
        else {
            elbowPivot.set(0);
        }
    }
    // Pivoting your elbow to meet the grids for scoring
    else if (angleRotation > 1) {
        if (error > 1 || error < -1) {

            speed = error * k;
            if (Math.abs(speed) > 0.5) {
                speed = Math.copySign(0.5, speed);
            }
            else if (Math.abs(speed) > 0.2) {
                speed = Math.copySign(0.2, speed);
            }

            elbowPivot.set(speed);

            } 
        else {
        // Stalling the pivot motor may need to change its method
            elbowPivot.set(0);
            }
        } 
    }
    
    /**
     * TeleOperated elbow pivot control based on holding buttons
     * Stalling the pivot is heavily subject to change
     */
     public void joystickControl() {
        //Arm Motor button control
        if (joystick.getRawButton(1) == true) {
            elbowPivot.set(1);
            armState = 0;
        }
        else if (joystick.getRawButton(2) == true) {
            elbowPivot.set(-1);
            armState = 0;
        }
        else {
            armState ++ ;
            if (armState == 1) {
                armPosition = elbowEncoder.getPosition();
            }
            armStall();
        }
    } 
    // Stalling the elbow
    public void armStall() {
        double error = armPosition - elbowEncoder.getPosition();
        double speed = 0;
        double k = 0.01;
        if (error > 1 || error < -1) {
            speed = error * k;
        }
        elbowPivot.set(speed);
    }

    // // TeleOp method for automatically moving elbow pivot: Pending deletion
    public void EncoderRotation() {

    if (joystick.getRawButtonPressed(3)) {
        // Placeholder angle for rotating arm to cube node
        angleRotation = 30;
    }
    else if (joystick.getRawButtonPressed(4)) {
        // Placeholder angle for rotating arm to cone node
        angleRotation = 35;
    }

    // W.I.P. motor target. angleRotation is (x degrees / 360)
    double motorTarget = (int) gearRatio * COUNTS_PER_ROTATION * angleRotation/360;
    double error = motorTarget - elbowEncoder.getPosition();
    
    // Pivoting the arm to target
    if (error > 1 || error < -1) {

        speed = error * k;
        if (Math.abs(speed) > 0.5) {
            speed = Math.copySign(0.5, speed);
        }
        else if (Math.abs(speed) > 0.2) {
            speed = Math.copySign(0.2, speed);
        }

        elbowPivot.set(speed);

        } 
    else {

        // Stalling the pivot motor may need to change its method
        elbowPivot.set(0);

        }
    }

}
