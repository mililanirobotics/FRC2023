package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClawConstants;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;


public class ClawSubsystem extends SubsystemBase {
    public DoubleSolenoid leftClaw;
    public DoubleSolenoid rightClaw;

    public ClawSubsystem() {
        //intializing solenoids
        leftClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClawConstants.kLeftClawForward, ClawConstants.kLeftClawReverse);
        rightClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, ClawConstants.kRightClawForward, ClawConstants.kRightClawReverse);

        //setting default solenoid state
        leftClaw.set(kForward);
        rightClaw.set(kForward);
    }

    //gets current claw state value
    public DoubleSolenoid.Value getClawState() {
        Value clawState = leftClaw.get();
        return clawState;
    }
}
