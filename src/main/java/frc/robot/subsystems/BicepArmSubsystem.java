package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.GenericEntry;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//constants
import frc.robot.Constants.ArmConstants; 

public class BicepArmSubsystem extends SubsystemBase {
    //double solenoid that controls the bicep 
    private DoubleSolenoid bicepArm;
    //safety
    private static boolean safetyOn;
    //widgets
    private GenericEntry safetyWidget;
    private GenericEntry outsideFrameWidget;

    //constructor
    public BicepArmSubsystem(ShuffleboardTab driverTab) {
        //initializing solenoid
        bicepArm = new DoubleSolenoid(PneumaticsModuleType.REVPH, ArmConstants.kArmForwardChannel, ArmConstants.kArmReverseChannel);
        
        //safety will be on by default
        safetyOn = true;
        
        //initializing widgets
        safetyWidget = driverTab.add("Safety", safetyOn).withSize(2, 1).getEntry();
        outsideFrameWidget = driverTab.add("Outside Frame", true).withSize(2, 1).getEntry();
    }

    public void updateState() {
        if(bicepState() == Value.kForward) {
            outsideFrameWidget.setBoolean(false);
        }
        else {
            outsideFrameWidget.setBoolean(true);
        }
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
        safetyWidget.setBoolean(safetyOn);
    }

    /**
     * Returns whether or not the safety is on
     * @return True if the safety is on, false otherwise
     */
    public boolean getSafety() {
        return safetyOn;
    }
}
