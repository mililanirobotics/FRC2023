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



public class Claw{

    DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    // DoubleSolenoid clawLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
    // DoubleSolenoid clawRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
    
    // CANSparkMax armMotor = new CANSparkMax(7, MotorType.kBrushless);
    // RelativeEncoder armMotorEncoder = armMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 42);
    
    Joystick joystick = new Joystick(0);
    int armState = 0;
    double armPosition = 0;
        
    public void robotIntial() {

        //sets default states to the Solenoids
        armExtension.set(kReverse);
        // clawLeft.set(kReverse);
        // clawRight.set(kReverse);

    }

    // Arm Extension control
    public void ArmExtension() {
        armExtension.toggle();
    }

    // public void joystickControl() {
    //     //Arm Motor control
    //     if (joystick.getRawButton(1) == true) {
    //         armMotor.set(1);
    //         armState = 0;
    //     }
    //     else if (joystick.getRawButton(2) == true) {
    //         armMotor.set(-1);
    //         armState = 0;
    //     }
    //     else {
    //         armState ++;
    //         if (armState == 1) {
    //             armPosition = armMotorEncoder.getPosition();
    //         }
    //         armStall();
    //     }
    // } 
    
    // public void armStall() {
    //     double error = armPosition - armMotorEncoder.getPosition();
    //     double speed = 0;
    //     double k = 0.01;
    //     if (error > 1 || error < -1) {
    //         speed = error * k;
    //     }
    //     armMotor.set(speed);
    // }
    
    // // Claw Solenoid control
    // public void clawToggle() { 
    //     clawLeft.toggle();
    //     clawRight.toggle();
    // }
}