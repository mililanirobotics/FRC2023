package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

//constants
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.RobotConstants; 

public class ElbowPivotSubsystem extends SubsystemBase {
    public CANSparkMax leftElbowPivot;
    // private CANSparkMax rightElbowPivot;
    public MotorControllerGroup elbowPivot;

    private RelativeEncoder leftElbowEncoder;

    public double angleRotation;

    public ElbowPivotSubsystem() {
        leftElbowPivot = new CANSparkMax(PivotConstants.kleftPivot, MotorType.kBrushless);
        // rightElbowPivot = new CANSparkMax(PivotConstants.kRightPivot, MotorType.kBrushless);

        // rightElbowPivot.setInverted(true);

        elbowPivot = new MotorControllerGroup(leftElbowPivot); //, rightElbowPivot);

        leftElbowEncoder = leftElbowPivot.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);

        leftElbowEncoder.setPositionConversionFactor(RobotConstants.kArmGearRatio * RobotConstants.kCountsPerRev * angleRotation/360);

        resetEncoders();

    }

    /**
     * Resets all of the pivot encoder values
    */
    public void resetEncoders() {
        leftElbowEncoder.setPosition(0);
    }
   
    /**
     * Returns the pivot enocder's target in counts
     */
    public double motorTarget() {
        return RobotConstants.kArmGearRatio * RobotConstants.kCountsPerRev * angleRotation/360;
    }

    /**
     * Returns how far off the pivot is from motorTarget
     */
    public double error() {
        return motorTarget() - leftElbowEncoder.getPosition();
    }

    // Stops all motor movement
    public void shutdown() {
        elbowPivot.set(0);
    }


}
