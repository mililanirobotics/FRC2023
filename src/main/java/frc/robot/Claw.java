package frc.robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Claw{

    DoubleSolenoid claw = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);


    public void clawInit() {
        claw.set(kForward);
    }
    
    // Claw Solenoid control
    public void clawToggle() { 
        claw.toggle();
    }
}