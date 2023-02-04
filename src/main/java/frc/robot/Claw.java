package frc.robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import javax.crypto.KeyGenerator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



public class TeleOp {

    DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    DoubleSolenoid clawLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
    DoubleSolenoid clawRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
    
    CANSparkMax armMotor = new CANSparkMax(7, MotorType.kBrushless);
    RelativeEncoder armMotorEncoder = armMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    
    Joystick joystick = new Joystick(0);
    int armState = 0;
    double armPosition = 0;
    
    public void robotIntial() {

        //sets default states to the Solenoids
        armExtension.set(kReverse);
        clawLeft.set(kReverse);
        clawRight.set(kReverse);

    }

    public void joystickControl() {

        //Arm Motor control
        if (joystick.getRawButton(1) == true) {
            armMotor.set(1);
            armState = 0;
        }
        else if (joystick.getRawButton(2) == true) {
            armMotor.set(-1);
            armState = 0;
        }
        else {
            armState =+ 1;
            if (armState == 1) {
                armPosition = armMotorEncoder.getPosition();
            }
            armMotor.set(0);
        }

        // Arm Extension control
        if (joystick.getRawButtonPressed(3) == true) {
            armExtension.toggle();
        }

        // Claw Solenoid control
        if (joystick.getRawButtonPressed(4) == true) { 
            clawLeft.toggle();
            clawRight.toggle();
        }

    }

    public void armStall() {
        // double kG = 0;
        // double kV = 0;
        double error = armPosition - armMotorEncoder.getPosition();
        double kP = 0.1;
        double kI =+ error/10;
        // double kD = 0.1;

        double speed = (error * kP) + kI;
        armMotor.set(speed);
    }
}
