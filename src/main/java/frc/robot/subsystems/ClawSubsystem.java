package frc.robot.subsystems;

//subsystem and commands imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.networktables.GenericEntry;
//constants
import frc.robot.Constants.ClawConstants;

public class ClawSubsystem extends SubsystemBase {
    //double solenoids that control the claw
    private DoubleSolenoid claw;
    //widgets
    private GenericEntry clawOpenWidget;

    //constructor
    public ClawSubsystem(ShuffleboardTab driverTab) {
        //intializing solenoids
        claw = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClawConstants.kClawForwardChannel, ClawConstants.kClawReverseChannel);

        //setting default solenoid state to kForward (claw open)
        claw.set(kForward);

        //initializing widget
        clawOpenWidget = driverTab.add("Claw Open", true).withSize(2, 1).getEntry();
    }

    /**
     * Tells whether the leftClaw and rightClaw pistons are on kForward or kReverse
     * @return The current state of the claw pistons
     */
    public DoubleSolenoid.Value getClawState() {
        return claw.get();
    }

    /**
     * Closes the claw (retracts both pistons)
     */
    public void closeClaw() {
        claw.set(kForward);
    }

    /**
     * Opens the claw (extends both pistons)
     */
    public void openClaw() {
        claw.set(kReverse);
    }

    /**
     * Toggles the claw's current state (both pisons)
     */
    public void toggleClaw() {
        claw.toggle();
        clawOpenWidget.setBoolean(getClawState() == Value.kForward);
    }
}
