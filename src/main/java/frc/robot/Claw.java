// package frc.robot;

// import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;

// public class Claw{

//     DoubleSolenoid clawLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
//     DoubleSolenoid clawRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);
        
//     public void clawInit() {
//         //sets default states to the Solenoids
//         clawLeft.set(kReverse);
//         clawRight.set(kReverse);
//     }
    
//     // Claw Solenoid control
//     public void clawToggle() { 
//         clawLeft.toggle();
//         clawRight.toggle();
//     }
// }