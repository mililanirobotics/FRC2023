package frc.robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Claw{

    DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    DoubleSolenoid clawLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
    DoubleSolenoid clawRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
    
    Joystick joystick = new Joystick(0);
        
    public void robotIntial() {

        //sets default states to the Solenoids
        armExtension.set(kReverse);
        clawLeft.set(kReverse);
        clawRight.set(kReverse);

    }

    // Arm Extension control
    public void ArmExtension() {
        armExtension.toggle();
    }

    
    // Claw Solenoid control
    public void clawToggle() { 
        clawLeft.toggle();
        clawRight.toggle();
    }
}