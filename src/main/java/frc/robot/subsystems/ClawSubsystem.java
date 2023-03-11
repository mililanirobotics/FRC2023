package frc.robot.subsystems;

//subsystem and commands imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
//constants
import frc.robot.Constants.ClawConstants;

public class ClawSubsystem extends SubsystemBase {
    //double solenoids that control the claw
    private DoubleSolenoid leftClaw;
    private DoubleSolenoid rightClaw;

    //constructor
    public ClawSubsystem() {
        //intializing solenoids
        leftClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClawConstants.kLeftClawForwardChannel, ClawConstants.kLeftClawReverseChannel);
        rightClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClawConstants.kRightClawForwardChannel, ClawConstants.kRightClawReverseChannel);

        //setting default solenoid state to kForward (claw open)
        leftClaw.set(kForward);
        rightClaw.set(kForward);
    }

     /**
     * Tells whether the leftClaw and rightClaw pistons are on kForward or kReverse
     * @return The current state of the claw pistons
     */
    public DoubleSolenoid.Value getClawState() {
        Value clawState = leftClaw.get();
        return clawState;
    }

    /**
     * Closes the claw (retracts both pistons)
     */
    public void closeClaw() {
        leftClaw.set(kReverse);
        rightClaw.set(kReverse);
    }

    /**
     * Opens the claw (extends both pistons)
     */
    public void openClaw() {
        leftClaw.set(kForward);
        rightClaw.set(kForward);
    }

    /**
     * Toggles the claw's current state (both pisons)
     */
    public void toggleClaw() {
        leftClaw.toggle();
        rightClaw.toggle();
    }
}
