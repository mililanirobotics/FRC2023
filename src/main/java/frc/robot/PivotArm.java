package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class PivotArm {
     
    // Intializing Motors and Motor Properties
     DoubleSolenoid bicepPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);


     public void bicepInit () {
        bicepPiston.set(kReverse);
     }
 
     // Toggling extension and retraction of bicep's piston
     public void bicepToggle() {
         bicepPiston.toggle();
     }
}
