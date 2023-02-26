package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


//constants
import frc.robot.Constants.PivotConstants;

public class PivotSubsystem extends SubsystemBase {
    private CANSparkMax pivot = new CANSparkMax(PivotConstants.kPivot, MotorType.kBrushless);
    private DoubleSolenoid arm = new DoubleSolenoid(PneumaticsModuleType.REVPH, PivotConstants.kArmForward, PivotConstants.kArmReverse);
}
