package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.PivotConstants; 

public class BicepArmSubsystem extends SubsystemBase {
    public DoubleSolenoid bicepArm;

    public BicepArmSubsystem() {
        bicepArm = new DoubleSolenoid(PneumaticsModuleType.REVPH, PivotConstants.kArmForward, PivotConstants.kArmReverse);
    }

    public DoubleSolenoid.Value bicepState() {
        return bicepArm.get();
    }
}
