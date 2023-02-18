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

    DoubleSolenoid clawLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    DoubleSolenoid clawRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);
        
    Joystick joystick = new Joystick(0);
    int armState = 0;
    double armPosition = 0;
        
    public void robotIntial() {

        //sets default states to the Solenoids
        clawLeft.set(kReverse);
        clawRight.set(kReverse);

    }
    
    // Claw Solenoid control
    public void clawToggle() { 
        clawLeft.toggle();
        clawRight.toggle();
    }
}