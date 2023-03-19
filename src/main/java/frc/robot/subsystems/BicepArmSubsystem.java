package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//constants
import frc.robot.Constants.PivotConstants; 

public class BicepArmSubsystem extends SubsystemBase {
    //double solenoid that controls the bicep 
    private DoubleSolenoid bicepArm;
    //safety
    private static boolean safetyOn;

    //constructor
    public BicepArmSubsystem() {
        //initializing solenoid
        bicepArm = new DoubleSolenoid(PneumaticsModuleType.REVPH, PivotConstants.kArmForwardChannel, PivotConstants.kArmReverseChannel);
        safetyOn = true;
    }

    /**
     * Tells whether the bicep piston is on kForward or kReverse
     * @return The current state of the bicep piston
     */
    public DoubleSolenoid.Value bicepState() {
        return bicepArm.get();
    }

    /**
     * Returns whether or not the bicep is extended inside or outside the frame
     * @return If the bicep piston is in the kForward state
     */
    public boolean outsideFrame() {
        return bicepState() == Value.kForward;
    }

    /**
     * Retracts the bicep piston (extends outside of frame)
     */
    public void retractBicep() {
        bicepArm.set(Value.kReverse);
    }

    /**
     * Extends the bicep piston (retracts back into the frame)
     */
    public void extendBicep() {
        bicepArm.set(Value.kForward);
    }

    /**
     * Toggles the current state of the bicep piston between kForward and kReverse
     */
    public void toggleBicep() {
        bicepArm.toggle();
    }

    /**
     * Toggles the safety on and off
     */
    public void toggleSafety() {
        if(safetyOn) {
            safetyOn = false;
        }
        else {
            safetyOn = true;
        }
    }
}
