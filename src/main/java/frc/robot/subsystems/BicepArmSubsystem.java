package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.PivotConstants; 

public class BicepArmSubsystem extends SubsystemBase {
    private DoubleSolenoid bicepArm;

    public BicepArmSubsystem() {
        bicepArm = new DoubleSolenoid(PneumaticsModuleType.REVPH, PivotConstants.kArmForward, PivotConstants.kArmReverse);
    }

    // Tells whether the Bicep Piston is on kForward or kReverse
    public DoubleSolenoid.Value bicepState() {
        return bicepArm.get();
    }

    // Retracts the Bicep Piston
    public void retractBicep() {
        bicepArm.set(Value.kReverse);
    }

    // Extends the Bicep Piston
    public void extendBicep() {
        bicepArm.set(Value.kForward);
    }

    // Toggles the Bicep Piston
    public void toggleBicep() {
        bicepArm.toggle();
    }
}
