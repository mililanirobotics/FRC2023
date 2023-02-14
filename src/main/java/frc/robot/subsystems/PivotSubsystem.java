package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

//constants
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.RobotConstants; 

public class PivotSubsystem extends SubsystemBase {
    private CANSparkMax pivot = new CANSparkMax(PivotConstants.kPivot, MotorType.kBrushless);
    private DoubleSolenoid arm = new DoubleSolenoid(PneumaticsModuleType.REVPH, PivotConstants.kArmForward, PivotConstants.kArmReverse);
}
