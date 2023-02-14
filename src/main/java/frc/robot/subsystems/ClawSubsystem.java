package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;


public class ClawSubsystem extends SubsystemBase {
    private DoubleSolenoid leftClaw;
    private DoubleSolenoid rightClaw;

    public ClawSubsystem() {
        leftClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 0);
        rightClaw = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 0);
    }
    
}
